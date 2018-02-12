package me.chriskyle.library.social.internal.wb

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.api.*
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.exception.WeiboException
import com.sina.weibo.sdk.net.RequestListener
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import com.sina.weibo.sdk.utils.Utility
import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.social.internal.SSOHandler
import me.chriskyle.library.social.internal.util.LogUtils
import me.chriskyle.library.social.internal.wb.api.UsersAPI
import me.chriskyle.library.social.internal.wb.api.model.User
import me.chriskyle.library.social.listener.AuthListener
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.*
import me.chriskyle.library.social.model.UserModel
import java.io.File

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
class WBHandler : SSOHandler {

    private var activity: Activity? = null

    private var authListener: AuthListener? = null
    private var shareListener: ShareListener? = null

    private var ssoHandler: SsoHandler? = null
    private var shareHandler: WbShareHandler? = null
    private var config: PlatformConfig.WB? = null

    override fun init(activity: Activity, platform: PlatformConfig.Platform) {
        this.activity = activity
        this.config = platform as PlatformConfig.WB
        val authInfo = AuthInfo(this.activity!!.applicationContext, this.config!!.appKey, REDIRECT_URL, SCOPE)
        WbSdk.install(this.activity!!.applicationContext, authInfo)
        this.ssoHandler = SsoHandler(this.activity)
        this.shareHandler = WbShareHandler(this.activity)
        this.shareHandler!!.registerApp()
    }

    override fun authorize(activity: Activity, authListener: AuthListener?) {
        this.authListener = authListener
        ssoHandler!!.authorize(object : WbAuthListener {

            override fun onSuccess(oauth2AccessToken: Oauth2AccessToken) {
                AccessTokenKeeper.writeAccessToken(activity, oauth2AccessToken)
                if (oauth2AccessToken.isSessionValid) {
                    val api = UsersAPI(activity, config!!.appKey, oauth2AccessToken)
                    val uid = java.lang.Long.parseLong(oauth2AccessToken.uid)
                    api.show(uid, object : RequestListener {
                        override fun onComplete(s: String) {
                            val userModel: UserModel
                            val user = User.parse(s)
                            if (user != null) {
                                userModel = UserModel(user.id, user.name, user.avatar_hd, user.gender)
                                this@WBHandler.authListener!!.onAuthComplete(config!!.name, userModel)
                            } else {
                                val errMsg = "errMsg=get user info occur error"
                                LogUtils.e(errMsg)
                                authListener!!.onAuthError(config!!.name, errMsg)
                            }
                        }

                        override fun onWeiboException(e: WeiboException) {
                            val errMsg = "errMsg=get user info occur error->" + e.message
                            LogUtils.e(errMsg)
                            authListener!!.onAuthError(config!!.name, errMsg)
                        }
                    })
                }

                val errMsg = "errMsg=oauth2AccessToken is not sessionValid"
                LogUtils.e(errMsg)
                authListener!!.onAuthError(config!!.name, errMsg)
            }

            override fun cancel() {
                authListener!!.onAuthCancel(config!!.name)
            }

            override fun onFailure(wbConnectErrorMessage: WbConnectErrorMessage) {
                val errMsg = "errMsg=" + wbConnectErrorMessage.errorMessage
                LogUtils.e(errMsg)
                authListener!!.onAuthError(config!!.name, errMsg)
            }
        })
    }

    override fun share(activity: Activity, baseShareMedia: BaseShareMedia, shareListener: ShareListener?) {
        this.activity = activity
        this.shareListener = shareListener

        val wbMessage = WeiboMultiMessage()

        when (baseShareMedia) {
            is ShareTextMedia -> {
                val textObject = TextObject()
                textObject.text = baseShareMedia.text
                textObject.title = baseShareMedia.title
                textObject.actionUrl = baseShareMedia.actionUrl
                wbMessage.textObject = textObject
            }
            is ShareTextImageMedia -> {
                if (!TextUtils.isEmpty(baseShareMedia.text)) {
                    val textObject = TextObject()
                    textObject.text = baseShareMedia.text
                    wbMessage.textObject = textObject
                }

                if (baseShareMedia.image != null) {
                    val imageObject = ImageObject()
                    imageObject.setImageObject(baseShareMedia.image)
                    wbMessage.imageObject = imageObject
                }
            }
            is ShareImageMedia -> {
                val imageObject = ImageObject()
                imageObject.setImageObject(baseShareMedia.image)
                wbMessage.imageObject = imageObject
            }
            is ShareWebMedia -> {
                val mediaObject = WebpageObject()
                mediaObject.identify = Utility.generateGUID()
                mediaObject.title = baseShareMedia.title
                mediaObject.description = baseShareMedia.description
                mediaObject.setThumbImage(baseShareMedia.thumb)
                mediaObject.actionUrl = baseShareMedia.webPageUrl
                mediaObject.defaultText = baseShareMedia.title
                wbMessage.mediaObject = mediaObject
            }
            is ShareVideoMedia -> {
                val videoSourceObject = VideoSourceObject()
                videoSourceObject.videoPath = Uri.fromFile(File(baseShareMedia.videoUrl!!))
                wbMessage.videoSourceObject = videoSourceObject
            }
            else -> {
                this.shareListener?.onShareError(this.config!!.name, "wb is not support this shareMedia")
                return
            }
        }

        WBShareCallbackActivity.launchShareActivity(activity, wbMessage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        ssoHandler?.authorizeCallBack(requestCode, resultCode, data)
    }

    override fun isInstall() = WbSdk.isWbInstall(this.activity)

    internal fun doTrickShare(wbMultiMessage: WeiboMultiMessage) {
        shareHandler?.shareMessage(wbMultiMessage, false)
    }

    internal fun newIntent(intent: Intent, wbShareCallback: WbShareCallback) {
        shareHandler?.doResultIntent(intent, wbShareCallback)
    }

    internal fun wbShareSuccess() {
        this.shareListener?.onShareComplete(this.config!!.name)
    }

    internal fun wbShareCancel() {
        this.shareListener?.onShareCancel(this.config!!.name)
    }

    internal fun wbShareFail(errMsg: String) {
        this.shareListener?.onShareError(this.config!!.name, errMsg)
    }

    companion object {

        private var REDIRECT_URL = ""
        private var SCOPE = ""

        fun setRedirectUrl(redirectUrl: String) {
            REDIRECT_URL = redirectUrl
        }

        fun setScope(Scope: String) {
            SCOPE = Scope
        }
    }
}
