package me.chriskyle.ikan.presentation.module.base.web.simple

import android.content.Intent
import android.text.TextUtils
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class SimpleWebPresenterImpl : BasePresenter<SimpleWebView>(), SimpleWebPresenter {

    override fun dispatchIntent(intent: Intent) {
        val title = intent.getStringExtra(SimpleWebActivity.EXTRA_KEY_TITLE)
        val url = intent.getStringExtra(SimpleWebActivity.EXTRA_KEY_URL)

        if (!TextUtils.isEmpty(title)) {
            view?.renderWebTitle(title)
        }

        if (!TextUtils.isEmpty(url)) {
            view?.renderWebView(url)
        }
    }
}