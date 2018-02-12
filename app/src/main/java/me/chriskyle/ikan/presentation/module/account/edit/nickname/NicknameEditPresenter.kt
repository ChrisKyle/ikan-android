package me.chriskyle.ikan.presentation.module.account.edit.nickname

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface NicknameEditPresenter : MvpPresenter<NicknameEditView> {

    fun loadAccount()

    fun nicknameEditTextChanged(char: CharSequence)
}