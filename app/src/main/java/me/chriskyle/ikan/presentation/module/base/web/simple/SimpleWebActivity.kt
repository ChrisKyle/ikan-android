package me.chriskyle.ikan.presentation.module.base.web.simple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import me.chriskyle.ikan.presentation.module.base.web.BaseWebActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class SimpleWebActivity : BaseWebActivity<SimpleWebView, SimpleWebPresenter>(), SimpleWebView {

    @Inject
    override lateinit var presenter: SimpleWebPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.dispatchIntent(intent)
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().simpleWebComponent(SimpleWebModule()).inject(this)
    }

    override fun renderWebTitle(title: String) {
        this.title?.text = title
    }

    override fun renderWebView(url: String) {
        webView?.loadUrl(url)
    }

    companion object {

        const val EXTRA_KEY_URL = "url"
        const val EXTRA_KEY_TITLE = "title"

        fun getCallingIntent(context: Context, url: String, title: String): Intent {
            val intent = Intent(context, SimpleWebActivity::class.java)
            intent.putExtra(EXTRA_KEY_URL, url)
            intent.putExtra(EXTRA_KEY_TITLE, title)

            return intent
        }
    }
}