package me.chriskyle.ikan.presentation.module.base.web

import android.view.ViewGroup
import android.webkit.WebViewClient
import butterknife.BindView
import butterknife.OnClick
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import me.chriskyle.library.design.ProgressWebView
import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView
import me.chriskyle.library.toolkit.utils.InputMethodUtils

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/13.
 */
abstract class BaseWebActivity<V : MvpView, P : MvpPresenter<V>> : BaseActivity<V, P>() {

    @BindView(R.id.web_view)
    @JvmField
    var webView: ProgressWebView? = null

    override val layout: Int
        get() = R.layout.activity_base_web

    override fun initView() {
        super.initView()

        webView?.webViewClient = object : WebViewClient() {}
    }

    override fun onResume() {
        super.onResume()

        webView?.onResume()
    }

    override fun onPause() {
        super.onPause()

        webView?.onPause()
    }

    override fun onDestroy() {
        if (webView != null) {
            webView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView?.clearHistory()
            (webView?.parent as ViewGroup).removeView(webView)
            webView?.destroy()
            webView = null
        }
        super.onDestroy()
    }

    @OnClick(R.id.back)
    override fun back() {
        InputMethodUtils.hideSoftInput(this)
        super.onBackPressed()
    }
}