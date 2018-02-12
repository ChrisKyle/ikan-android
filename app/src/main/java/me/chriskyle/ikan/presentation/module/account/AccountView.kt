package me.chriskyle.ikan.presentation.module.account

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AccountView : MvpView {

    fun renderAvatar(avatarUrl: String?)

    fun renderNickname(nickname: String?)

    fun renderGender(gender: String)

    fun renderBalance(balance: Long)

    fun showLoggedStatus()

    fun showUnLoginStatus()
}