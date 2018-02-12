package me.chriskyle.ikan.presentation.module.account

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AccountPresenter : MvpPresenter<AccountView> {

    fun checkLoginStatus()

    fun accountEdit()

    fun notification()

    fun assets()

    fun setting()

    fun download()

    fun upload()

    fun watchHistory()
}