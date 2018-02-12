package me.chriskyle.library.social

import android.app.Activity
import android.content.Intent
import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.internal.SSOHandler
import me.chriskyle.library.social.internal.qq.QQHandler
import me.chriskyle.library.social.internal.wb.WBHandler
import me.chriskyle.library.social.internal.wx.WXHandler
import me.chriskyle.library.social.listener.AuthListener
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.BaseShareMedia
import java.util.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
class SocialApi private constructor() {

    private val ssoHandlerMap = WeakHashMap<PlatformType, SSOHandler>()

    fun getSSOHandler(platformType: PlatformType): SSOHandler {
        if (ssoHandlerMap[platformType] == null) {
            when (platformType) {
                PlatformType.WX -> ssoHandlerMap.put(platformType, WXHandler())

                PlatformType.WX_TIMELINE -> ssoHandlerMap.put(platformType, WXHandler())

                PlatformType.QQ -> ssoHandlerMap.put(platformType, QQHandler())

                PlatformType.QQ_ZONE -> ssoHandlerMap.put(platformType, QQHandler())

                PlatformType.WB -> ssoHandlerMap.put(platformType, WBHandler())
            }
        }

        return ssoHandlerMap[platformType]!!
    }

    fun doAuthVerify(activity: Activity, platformType: PlatformType, authListener: AuthListener) {
        val ssoHandler = getSSOHandler(platformType)
        with(ssoHandler) {
            init(activity, PlatformConfig.getPlatformConfig(platformType)!!)
            authorize(activity, authListener)
        }
    }

    fun doShare(activity: Activity, platformType: PlatformType, shareMedia: BaseShareMedia, shareListener: ShareListener) {
        val ssoHandler = getSSOHandler(platformType)
        with(ssoHandler) {
            init(activity, PlatformConfig.getPlatformConfig(platformType)!!)
            share(activity, shareMedia, shareListener)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        for ((_, ssoHandler) in ssoHandlerMap) {
            ssoHandler?.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {

        val instance = SocialApi()
    }
}
