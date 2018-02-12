package me.chriskyle.ikan.presentation.module.base.web.simple

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface SimpleWebView : MvpView {

    fun renderWebTitle(title: String)

    fun renderWebView(url: String)
}