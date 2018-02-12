package me.chriskyle.ikan.presentation.module.account.edit.nickname

import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class NicknameEditPresenterImpl(val bus: Bus, val accountRepositoryManager: AccountRepositoryManager) :
        BasePresenter<NicknameEditView>(), NicknameEditPresenter {

    override fun loadAccount() {
        val account = accountRepositoryManager.executeGetLogged()
        if (account != null) {
            view?.renderNicknameHint(account.nickname)
        }
    }

    override fun nicknameEditTextChanged(char: CharSequence) {
        if (char.isNotEmpty()) {
            view?.enableSend()
        } else {
            view?.disableSend()
        }
    }
}