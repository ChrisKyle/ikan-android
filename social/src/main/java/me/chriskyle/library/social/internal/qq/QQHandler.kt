package me.chriskyle.library.social.internal.qq

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import com.tencent.connect.UserInfo
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.internal.SSOHandler
import me.chriskyle.library.social.internal.util.BitmapUtils
import me.chriskyle.library.social.internal.util.LogUtils
import me.chriskyle.library.social.listener.AuthListener
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.BaseShareMedia
import me.chriskyle.library.social.media.ShareImageMedia
import me.chriskyle.library.social.media.ShareMusicMedia
import me.chriskyle.library.social.media.ShareWebMedia
import me.chriskyle.library.social.model.UserModel
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
class QQHandler : SSOHandler {

    private var activity: Activity? = null

    private var authListener: AuthListener? = null
    private var shareListener: ShareListener? = null

    private var tencent: Tencent? = null
    private var config: PlatformConfig.QQ? = null

    override fun init(activity: Activity, platform: PlatformConfig.Platform) {
        this.activity = activity
        this.config = platform as PlatformConfig.QQ
        this.tencent = Tencent.createInstance(this.config!!.appId, this.activity!!.applicationContext)
    }

    override fun authorize(activity: Activity, authListener: AuthListener?) {
        this.activity = activity
        this.authListener = authListener

        this.tencent!!.login(this.activity, "all", object : IUiListener {

            override fun onComplete(o: Any?) {
                if (null == o) {
                    authListener?.onAuthError(config!!.name, "onShareComplete response=null")
                    return
                }

                val response = o as JSONObject
                initOpenidAndToken(response)

                val info = UserInfo(activity, tencent!!.qqToken)
                info.getUserInfo(object : IUiListener {

                    override fun onComplete(o: Any) {
                        val jsonObject = o as JSONObject

                        val userModel: UserModel
                        try {
                            val nickname = jsonObject.getString("nickname")
                            val avatarUrl = jsonObject.getString("figureurl_qq_2")
                            val sex = jsonObject.getString("gender")
                            userModel = UserModel(tencent!!.openId, nickname, avatarUrl, sex)
                            authListener?.onAuthComplete(config!!.name, userModel)
                            tencent!!.logout(activity)
                        } catch (e: JSONException) {
                        }
                    }

                    override fun onError(uiError: UiError) {
                        val errMsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail
                        LogUtils.e(errMsg)
                        authListener?.onAuthError(config!!.name, errMsg)
                    }

                    override fun onCancel() {
                        authListener?.onAuthCancel(config!!.name)
                    }
                })
            }

            override fun onError(uiError: UiError) {
                val errMsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail
                LogUtils.e(errMsg)
                authListener?.onAuthError(config!!.name, errMsg)
            }

            override fun onCancel() {
                authListener?.onAuthCancel(config!!.name)
            }
        })
    }

    override fun share(activity: Activity, baseShareMedia: BaseShareMedia, shareListener: ShareListener?) {
        this.activity = activity
        this.shareListener = shareListener

        val path = Environment.getExternalStorageDirectory().toString() + "/socail_qq_img_tmp.png"
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }

        val params = Bundle()

        if (this.config!!.name === PlatformType.QQ_ZONE) {
            if (baseShareMedia is ShareWebMedia) {

                BitmapUtils.saveBitmapFile(baseShareMedia.thumb, path)

                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, baseShareMedia.title)
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, baseShareMedia.description)
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, baseShareMedia.webPageUrl)

                val paths = ArrayList<String>()
                paths.add(path)
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, paths)
            } else {
                this.shareListener?.onShareError(this.config!!.name, "QQ Zone is not support this shareMedia")
                return
            }

            this.tencent!!.shareToQzone(this.activity, params, object : IUiListener {
                override fun onComplete(o: Any) {
                    shareListener!!.onShareComplete(config!!.name)
                }

                override fun onError(uiError: UiError) {
                    val errMsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail
                    LogUtils.e(errMsg)
                    shareListener?.onShareError(config!!.name, errMsg)
                }

                override fun onCancel() {
                    shareListener?.onShareCancel(config!!.name)
                }
            })
        } else {
            when (baseShareMedia) {
                is ShareWebMedia -> {
                    BitmapUtils.saveBitmapFile(baseShareMedia.thumb, path)

                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, baseShareMedia.title)
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, baseShareMedia.description)
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, baseShareMedia.webPageUrl)
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path)
                }
                is ShareImageMedia -> {
                    BitmapUtils.saveBitmapFile(baseShareMedia.image, path)

                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path)
                }
                is ShareMusicMedia -> {
                    BitmapUtils.saveBitmapFile(baseShareMedia.thumb, path)

                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO)
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, baseShareMedia.title)
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, baseShareMedia.description)
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, baseShareMedia.musicUrl)
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path)
                    params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, baseShareMedia.musicUrl)
                }
                else -> {
                    this.shareListener?.onShareError(this.config!!.name, "QQ is not support this shareMedia")
                    return
                }
            }

            tencent?.shareToQQ(this.activity, params, object : IUiListener {

                override fun onComplete(o: Any) {
                    shareListener?.onShareComplete(config!!.name)
                }

                override fun onError(uiError: UiError) {
                    val errMsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail
                    LogUtils.e(errMsg)
                    shareListener?.onShareError(config!!.name, errMsg)
                }

                override fun onCancel() {
                    shareListener?.onShareCancel(config!!.name)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Tencent.onActivityResultData(requestCode, resultCode, data, null)
    }

    override fun isInstall() = tencent?.isSupportSSOLogin(activity)!!

    private fun initOpenidAndToken(jsonObject: JSONObject) {
        try {
            val token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN)
            val expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN)
            val openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID)

            tencent!!.setAccessToken(token, expires)
            tencent!!.openId = openId
        } catch (e: Exception) {
        }
    }
}
