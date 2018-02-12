package me.chriskyle.library.social.internal.wb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.share.WbShareCallback

import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.R
import me.chriskyle.library.social.SocialApi

/**
 * Description :
 *
 *
 * Created by Chris Kyle on 2017/11/21.
 */
class WBShareCallbackActivity : Activity(), WbShareCallback {

    private var wbHandler: WBHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val socialApi = SocialApi.instance
        this.wbHandler = socialApi.getSSOHandler(PlatformType.WB) as WBHandler?
        this.wbHandler!!.init(this, PlatformConfig.getPlatformConfig(PlatformType.WB)!!)

        intent.let {
            val wbMessageBundle = intent.getBundleExtra(INTENT_EXTRA_KEY)
            if (wbMessageBundle != null) {
                val wbMessage = WeiboMultiMessage()
                wbMessage.toObject(wbMessageBundle)
                wbHandler!!.doTrickShare(wbMessage)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        this.wbHandler!!.newIntent(intent, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        this.wbHandler!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onWbShareSuccess() {
        this.wbHandler!!.wbShareSuccess()

        finish()
    }

    override fun onWbShareCancel() {
        this.wbHandler!!.wbShareCancel()

        finish()
    }

    override fun onWbShareFail() {
        this.wbHandler!!.wbShareFail(getString(R.string.share_fail))

        finish()
    }

    companion object {

        private val INTENT_EXTRA_KEY = "extra_key_wb_message"

        internal fun launchShareActivity(context: Context, wbMultiMessage: WeiboMultiMessage) {
            val intent = Intent(context, WBShareCallbackActivity::class.java)
            intent.putExtra(INTENT_EXTRA_KEY, wbMultiMessage.toBundle(Bundle()))
            context.startActivity(intent)
        }
    }
}
