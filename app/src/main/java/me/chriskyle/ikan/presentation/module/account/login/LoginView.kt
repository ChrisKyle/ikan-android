package me.chriskyle.ikan.presentation.module.account.login

import me.chriskyle.library.mvp.MvpView
import me.chriskyle.library.support.InsLoadingView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface LoginView : MvpView {

    fun renderQQLoginStatus(status: InsLoadingView.Status)

    fun renderWXLoginStatus(status: InsLoadingView.Status)

    fun renderWBLoginStatus(status: InsLoadingView.Status)

    fun showLoginSuccess()
}