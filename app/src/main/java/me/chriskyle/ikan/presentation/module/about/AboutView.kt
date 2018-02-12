package me.chriskyle.ikan.presentation.module.about

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AboutView : MvpView {

    fun renderVersion(version: String)

    fun showSharePopupWindow()
}