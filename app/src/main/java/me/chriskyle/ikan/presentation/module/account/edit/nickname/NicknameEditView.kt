package me.chriskyle.ikan.presentation.module.account.edit.nickname

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface NicknameEditView : MvpView {

    fun renderNicknameHint(nickname: String)

    fun enableSend()

    fun disableSend()
}