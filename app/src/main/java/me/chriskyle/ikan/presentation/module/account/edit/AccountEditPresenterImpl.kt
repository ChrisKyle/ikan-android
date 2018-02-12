package me.chriskyle.ikan.presentation.module.account.edit

import android.app.Activity
import android.content.Intent
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.event.AccountModifyEvent
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AccountEditPresenterImpl(val bus: Bus, val accountRepositoryManager: AccountRepositoryManager) :
        BasePresenter<AccountEditView>(), AccountEditPresenter {

    override fun onViewAttached() {
        super.onViewAttached()
        bus.subscribe(this, bus.obtainSubscriber(AccountModifyEvent::class.java, Consumer { event ->
            onAccountModifyEvent(event)
        }))
    }

    override fun onViewDeAttached() {
        bus.unSubscribe(this)
        super.onViewDeAttached()
    }

    override fun loadAccount() {
        val account = accountRepositoryManager.executeGetLogged()
        account?.let {
            with(view!!) {
                renderAvatar(account.avatarUrl)
                renderNickname(account.nickname)
                renderGender(account.gender)
            }
        }

    }

    override fun editNickname() {
        Router.routerToNicknameEdit(view?.ctx)
    }

    override fun genderChanged(id: Int) {
        val account = accountRepositoryManager.executeGetLogged()
        val gender: String = if (id == 0) {
            view?.ctx?.getString(R.string.account_edit_sex_male)!!
        } else {
            view?.ctx?.getString(R.string.account_edit_sex_female)!!
        }
        view?.renderGender(gender)

        val accountParamProvider = AccountParamProvider()
        accountParamProvider.accountId(account!!.id)
        accountParamProvider.gender(gender)

        accountRepositoryManager.executeUpdate(accountParamProvider, UpdateGenderObserver())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data.let {
            if (resultCode != Activity.RESULT_OK) {
                return
            }

            if (requestCode == REQUEST_CODE_CHOOSE_IMAGE) {
                updateAvatar(data)
            } else if (requestCode == REQUEST_CODE_EDIT_NICKNAME) {
                updateNickname(data)
            }
        }
    }

    private fun onAccountModifyEvent(event: AccountModifyEvent) {
        event.account?.let {
            view?.renderNickname(event.account?.nickname)
        }
    }

    private fun updateAvatar(data: Intent?) {
    }

    private fun updateNickname(data: Intent?) {
        data?.let {
            val nickname = data.getStringExtra(EXTRA_KEY_NICKNAME)
            view?.renderNickname(nickname)

            val account = accountRepositoryManager.executeGetLogged()
            val accountParamProvider = AccountParamProvider()
            accountParamProvider.accountId(account!!.id)
            accountParamProvider.nickname(nickname)

            accountRepositoryManager.executeUpdate(accountParamProvider, UpdateNicknameObserver())
        }
    }

    private fun updateNicknameSuccess(accountEntity: AccountEntity) {
        view?.let {
            view?.showEditNicknameSuccess()
        }

        bus.post(AccountModifyEvent(accountEntity))
    }

    private fun updateNicknameError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showEditNicknameError(msg)
    }

    private fun updateGenderSuccess(accountEntity: AccountEntity) {
        view?.showEditGenderSuccess()

        bus.post(AccountModifyEvent(accountEntity))
    }

    private fun updateGenderError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showEditGenderError(msg)
    }

    inner class UpdateNicknameObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            updateNicknameSuccess(t)
        }

        override fun onError(e: Throwable) {
            updateNicknameError(e)
        }
    }

    inner class UpdateGenderObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            updateGenderSuccess(t)
        }

        override fun onError(e: Throwable) {
            updateGenderError(e)
        }
    }

    companion object {

        const val REQUEST_CODE_CHOOSE_IMAGE = 0x0
        const val REQUEST_CODE_EDIT_NICKNAME = 0x1

        const val EXTRA_KEY_NICKNAME = "nickname"
    }
}