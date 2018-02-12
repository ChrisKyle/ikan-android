package me.chriskyle.library.social.wx

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.tencent.mm.sdk.modelbase.BaseReq
import com.tencent.mm.sdk.modelbase.BaseResp
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler

import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.SocialApi
import me.chriskyle.library.social.internal.wx.WXHandler

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
open class WXCallbackActivity : Activity(), IWXAPIEventHandler {

    private var wxHandler: WXHandler? = null
    private var wxTimelineHandler: WXHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = SocialApi.instance

        this.wxHandler = api.getSSOHandler(PlatformType.WX) as WXHandler?
        this.wxHandler?.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX)!!)

        this.wxTimelineHandler = api.getSSOHandler(PlatformType.WX_TIMELINE) as WXHandler?
        this.wxTimelineHandler?.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX_TIMELINE)!!)

        this.wxHandler?.wxApi?.handleIntent(this.intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val api = SocialApi.instance

        this.wxHandler = api.getSSOHandler(PlatformType.WX) as WXHandler?
        this.wxHandler?.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX)!!)

        this.wxTimelineHandler = api.getSSOHandler(PlatformType.WX_TIMELINE) as WXHandler?
        this.wxTimelineHandler?.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX_TIMELINE)!!)

        this.wxHandler?.wxApi?.handleIntent(this.intent, this)
    }

    override fun onResp(resp: BaseResp?) {
        if (resp != null) {
            try {
                this.wxTimelineHandler?.wxEventHandler?.onResp(resp)
                this.wxHandler?.wxEventHandler?.onResp(resp)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        this.finish()
    }

    override fun onReq(req: BaseReq) {
        this.wxHandler?.wxEventHandler?.onReq(req)

        this.finish()
    }
}
