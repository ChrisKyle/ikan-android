package me.chriskyle.ikan.presentation.module.account.bind

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AccountBindPresenterImpl @Inject constructor(private val accountRepositoryManager: AccountRepositoryManager) :
        BasePresenter<AccountBindView>(), AccountBindPresenter {

    override fun qqBind() {
        view?.showUnbindQQDialog()
    }

    override fun wxBind() {
        view?.showUnbindWXDialog()
    }

    override fun wbBind() {
        view?.showUnbindWBDialog()
    }

    fun loadAccountDetailSuccess(accountEntity: AccountEntity) {}

    fun loadAccountDetailError(e: Throwable) {}

    fun bindSuccess(accountEntity: AccountEntity) {}

    fun bindError(e: Throwable) {}

    fun unbindSuccess(accountEntity: AccountEntity) {}

    fun unbindError(e: Throwable) {}

    inner class LoadAccountDetailObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            loadAccountDetailSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadAccountDetailError(e)
        }
    }

    inner class BindObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            bindSuccess(t)
        }

        override fun onError(e: Throwable) {
            bindError(e)
        }
    }

    inner class UnbindObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            unbindSuccess(t)
        }

        override fun onError(e: Throwable) {
            unbindError(e)
        }
    }
}