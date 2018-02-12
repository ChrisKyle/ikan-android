package me.chriskyle.ikan.presentation.module.base.web.simple

import android.content.Intent
import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface SimpleWebPresenter : MvpPresenter<SimpleWebView> {

    fun dispatchIntent(intent: Intent)
}
