package me.chriskyle.library.social.internal.wx

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.tencent.mm.sdk.constants.ConstantsAPI
import com.tencent.mm.sdk.modelbase.BaseReq
import com.tencent.mm.sdk.modelbase.BaseResp
import com.tencent.mm.sdk.modelmsg.*
import com.tencent.mm.sdk.openapi.IWXAPI
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler
import com.tencent.mm.sdk.openapi.WXAPIFactory
import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.internal.SSOHandler
import me.chriskyle.library.social.internal.util.BitmapUtils
import me.chriskyle.library.social.internal.util.LogUtils
import me.chriskyle.library.social.internal.wx.api.WXApi
import me.chriskyle.library.social.listener.AuthListener
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.*
import me.chriskyle.library.social.model.UserModel

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
class WXHandler : SSOHandler {

    private var activity: Activity? = null

    private var authListener: AuthListener? = null
    private var shareListener: ShareListener? = null

    internal var wxApi: IWXAPI? = null
        private set
    internal var wxEventHandler: IWXAPIEventHandler
    private var lastTransaction = ""

    private var platForm: PlatformConfig.WX? = null

    init {
        this.wxEventHandler = object : IWXAPIEventHandler {

            override fun onResp(resp: BaseResp) {
                if (lastTransaction != resp.transaction) {
                    return
                }

                val type = resp.type
                when (type) {
                    ConstantsAPI.COMMAND_SENDAUTH -> onAuthCallback(resp as SendAuth.Resp)

                    ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> onShareCallback(resp as SendMessageToWX.Resp)
                }
            }

            override fun onReq(req: BaseReq) {}
        }
    }

    override fun init(activity: Activity, platform: PlatformConfig.Platform) {
        this.activity = activity
        this.platForm = platform as PlatformConfig.WX

        this.wxApi = WXAPIFactory.createWXAPI(this.activity!!.applicationContext, this.platForm!!.appId)
        this.wxApi?.registerApp(this.platForm!!.appId)
    }

    override fun authorize(activity: Activity, authListener: AuthListener?) {
        this.activity = activity
        this.authListener = authListener

        val req1 = SendAuth.Req()
        req1.scope = SCOPE
        req1.state = STATE
        req1.transaction = buildTransaction("authorize")
        lastTransaction = req1.transaction
        if (!this.wxApi!!.sendReq(req1)) {
            LogUtils.e("wxapi sendReq fail")
            this.authListener?.onAuthError(this.platForm!!.name, "sendReq fail")
        }
    }

    private fun onAuthCallback(resp: SendAuth.Resp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> getAccessToken(resp)

            BaseResp.ErrCode.ERR_USER_CANCEL -> if (this.authListener != null) {
                this.authListener?.onAuthCancel(PlatformType.WX)
            }

            else -> {
                val errMsg = "wx auth error :" + resp.errCode.toString() + "->" + resp.errStr
                authListener?.onAuthError(PlatformType.WX, errMsg)
            }
        }
    }

    private fun getAccessToken(resp: SendAuth.Resp) {
        WXApi.getAccessToken(platForm!!.appId, platForm!!.secret, resp.code, object : WXApi.Callback {

            override fun onComplete(data: Map<String, String>) {
                getUserInfo(data["openid"], data["access_token"])
            }

            override fun onError(msg: String) {
                val errMsg = "wx auth error :" + msg
                authListener?.onAuthError(PlatformType.WX, errMsg)
            }
        })
    }

    private fun getUserInfo(openid: String?, accessToken: String?) {
        WXApi.getUserInfo(openid, accessToken, object : WXApi.Callback {

            override fun onComplete(data: MutableMap<String, String>?) {
                data?.let {
                    authListener?.onAuthComplete(PlatformType.WX, UserModel(data["openid"], data["nickname"],
                            data["headimgurl"], data["gender"]))
                }
            }

            override fun onError(msg: String?) {
                val errMsg = "wx auth error :" + msg
                authListener?.onAuthError(PlatformType.WX, errMsg)
            }
        })
    }

    override fun share(activity: Activity, baseShareMedia: BaseShareMedia, shareListener: ShareListener?) {
        this.activity = activity
        this.shareListener = shareListener

        val msg = WXMediaMessage()
        val type: String

        when (baseShareMedia) {
            is ShareWebMedia -> {
                type = "webpage"

                val webpageObject = WXWebpageObject()
                webpageObject.webpageUrl = baseShareMedia.webPageUrl

                msg.mediaObject = webpageObject
                msg.title = baseShareMedia.title
                msg.description = baseShareMedia.description
                msg.thumbData = BitmapUtils.bitmap2Bytes(baseShareMedia.thumb)
            }
            is ShareTextMedia -> {
                type = "text"

                val textObject = WXTextObject()
                textObject.text = baseShareMedia.text

                msg.mediaObject = textObject
                msg.description = baseShareMedia.text
            }
            is ShareImageMedia -> {
                type = "image"

                val imageObject = WXImageObject()
                //image限制10M
                imageObject.imageData = BitmapUtils.compressBitmap(BitmapUtils.bitmap2Bytes(baseShareMedia.image), 10 * 1024 * 1024)
                msg.mediaObject = imageObject

                val thumb = Bitmap.createScaledBitmap(baseShareMedia.image!!, 200, 200, true)
                msg.thumbData = BitmapUtils.bitmap2Bytes(thumb)
                thumb.recycle()
            }
            is ShareMusicMedia -> {
                type = "music"

                val musicObject = WXMusicObject()
                musicObject.musicUrl = baseShareMedia.musicUrl

                msg.mediaObject = musicObject
                msg.title = baseShareMedia.title
                msg.description = baseShareMedia.description
                msg.thumbData = BitmapUtils.bitmap2Bytes(baseShareMedia.thumb)
            }
            is ShareVideoMedia -> {
                type = "video"

                val videoObject = WXVideoObject()
                videoObject.videoUrl = baseShareMedia.videoUrl

                msg.mediaObject = videoObject
                msg.title = baseShareMedia.title
                msg.description = baseShareMedia.description
                msg.thumbData = BitmapUtils.bitmap2Bytes(baseShareMedia.thumb)
            }
            else -> {
                this.shareListener?.onShareError(this.platForm!!.name, "weixin is not support this shareMedia")
                return
            }
        }

        val req = SendMessageToWX.Req()
        req.message = msg
        req.transaction = buildTransaction(type)
        lastTransaction = req.transaction

        if (this.platForm!!.name === PlatformType.WX) {
            req.scene = SendMessageToWX.Req.WXSceneSession
        } else if (this.platForm!!.name === PlatformType.WX_TIMELINE) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline
        }

        if (!this.wxApi!!.sendReq(req)) {
            LogUtils.e("wxapi sendReq fail")
            this.shareListener?.onShareError(this.platForm!!.name, "sendReq fail")
        }
    }

    private fun onShareCallback(resp: com.tencent.mm.sdk.modelmsg.SendMessageToWX.Resp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> this.shareListener?.onShareComplete(this.platForm!!.name)

            BaseResp.ErrCode.ERR_USER_CANCEL -> this.shareListener?.onShareCancel(this.platForm!!.name)

            else -> {
                val err = "weixin share error (" + resp.errCode.toString() + "):" + resp.errStr
                shareListener?.onShareError(this.platForm!!.name, err)
            }
        }
    }

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }

    override fun isInstall() = this.wxApi!!.isWXAppInstalled

    companion object {

        private val SCOPE = "snsapi_userinfo"
        private val STATE = "none"
    }
}
