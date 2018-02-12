package me.chriskyle.ikan.presentation.module.account.login

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface LoginPresenter : MvpPresenter<LoginView> {

    fun qqLogin()

    fun wxLogin()

    fun wbLogin()

    fun resume()
}