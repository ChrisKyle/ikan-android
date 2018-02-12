package me.chriskyle.library.social.internal

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import me.chriskyle.library.social.internal.wb.WBHandler

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
object PlatformConfig {

    private val platforms = mutableMapOf<PlatformType, Platform>()

    init {
        platforms.put(PlatformType.WX, WX(PlatformType.WX))
        platforms.put(PlatformType.WX_TIMELINE, WX(PlatformType.WX_TIMELINE))
        platforms.put(PlatformType.QQ, QQ(PlatformType.QQ))
        platforms.put(PlatformType.QQ_ZONE, QQ(PlatformType.QQ_ZONE))
        platforms.put(PlatformType.WB, WB(PlatformType.WB))
    }

    fun init(pContext: Context) {
        var context = pContext
        context = context.applicationContext

        var appInfo: ApplicationInfo? = null
        try {
            appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        if (appInfo != null && appInfo.metaData != null) {
            if (!TextUtils.isEmpty(appInfo.metaData.getString("TENCENT_ID", ""))) {
                setQQ(appInfo.metaData.getString("TENCENT_ID", ""))
            } else {
                setQQ(appInfo.metaData.getInt("TENCENT_ID").toString())
            }
            setWX(appInfo.metaData.getString("WEIXIN_ID", ""),
                    appInfo.metaData.getString("WEIXIN_SECRET", ""))

            if (!TextUtils.isEmpty(appInfo.metaData.getString("WEIBO_APP_KEY", ""))) {
                setWB(appInfo.metaData.getString("WEIBO_APP_KEY", ""))
            } else {
                setWB(appInfo.metaData.getInt("WEIBO_APP_KEY").toString())
            }
            WBHandler.setRedirectUrl(appInfo.metaData.getString("WEIBO_REDIRECT_URL", ""))
            WBHandler.setScope(appInfo.metaData.getString("WEIBO_SCOPE", ""))
        } else {
            throw IllegalStateException("error load social config")
        }
    }

    //qq
    class QQ internal constructor(override val name: PlatformType) : Platform {
        var appId: String? = null
    }

    private fun setQQ(appId: String) {
        val qq = platforms[PlatformType.QQ] as QQ
        qq.appId = appId

        val qZone = platforms[PlatformType.QQ_ZONE] as QQ
        qZone.appId = appId
    }

    //wx
    class WX internal constructor(override val name: PlatformType) : Platform {
        var appId: String? = null
        var secret: String? = null
    }

    private fun setWX(appId: String, secret: String) {
        val wx = platforms[PlatformType.WX] as WX
        wx.appId = appId
        wx.secret = secret

        val wxTimeline = platforms[PlatformType.WX_TIMELINE] as WX
        wxTimeline.appId = appId
    }

    //wb
    class WB internal constructor(override val name: PlatformType) : Platform {
        var appKey: String? = null
    }

    private fun setWB(appKey: String) {
        val wx = platforms[PlatformType.WB] as WB
        wx.appKey = appKey
    }

    fun getPlatformConfig(platformType: PlatformType): Platform? {
        return platforms[platformType]
    }

    interface Platform {

        val name: PlatformType
    }
}
