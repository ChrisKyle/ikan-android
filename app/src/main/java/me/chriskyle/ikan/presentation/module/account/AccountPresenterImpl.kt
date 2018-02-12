package me.chriskyle.ikan.presentation.module.account

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.event.AccountModifyEvent
import me.chriskyle.ikan.presentation.event.LoginEvent
import me.chriskyle.ikan.presentation.event.LogoutEvent
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AccountPresenterImpl(private val bus: Bus, private val accountRepositoryManager: AccountRepositoryManager) :
        BasePresenter<AccountView>(), AccountPresenter {

    override fun onViewAttached() {
        super.onViewAttached()
        bus.subscribe(this, bus.obtainSubscriber(LoginEvent::class.java, Consumer { event ->
            onLoginEvent(event)
        }))
        bus.subscribe(this, bus.obtainSubscriber(LogoutEvent::class.java, Consumer { event ->
            onLogoutEvent(event)
        }))
        bus.subscribe(this, bus.obtainSubscriber(AccountModifyEvent::class.java, Consumer { event ->
            onAccountModifyEvent(event)
        }))
    }

    override fun onViewDeAttached() {
        bus.unSubscribe(this)
        super.onViewDeAttached()
    }

    override fun checkLoginStatus() {
        val account = accountRepositoryManager.executeGetLogged()
        when (account) {
            null -> view?.showUnLoginStatus()
            else -> {
                view?.showLoggedStatus()

                showAccountInView(account)
                loadNewestAccount(account)
            }
        }
    }

    override fun accountEdit() {
        Router.routeToAccountEdit(view?.ctx)
    }

    override fun notification() {
        Router.routeToNotification(view?.ctx)
    }

    override fun assets() {
        Router.routeToAccountAssets(view?.ctx)
    }

    override fun setting() {
        Router.routeToSetting(view?.ctx)
    }

    override fun download() {
        Router.routeToDownload(view?.ctx)
    }

    override fun upload() {
        Router.routeToUpload(view?.ctx)
    }

    override fun watchHistory() {
        Router.routeToWatchHistory(view?.ctx)
    }

    private fun onLoginEvent(event: LoginEvent) {
        view?.renderAvatar(event.accountEntity.avatarUrl)
        view?.showLoggedStatus()
    }

    private fun onLogoutEvent(event: LogoutEvent) {
        view?.renderAvatar(null)
        view?.showUnLoginStatus()
    }

    private fun onAccountModifyEvent(modifyEvent: AccountModifyEvent) {
        modifyEvent.account?.let {
            with(view!!) {
                renderAvatar(modifyEvent.account?.avatarUrl)
                renderNickname(modifyEvent.account?.nickname)
                renderGender(modifyEvent.account?.gender!!)
            }
        }
    }

    private fun loadNewestAccount(accountEntity: AccountEntity) {
        accountRepositoryManager.executeGetDetail(AccountParamProvider().accountId(accountEntity.id),
                LoadAccountObserver())
    }

    private fun showAccountInView(account: AccountEntity) {
        view?.let {
            with(view!!) {
                renderAvatar(account.avatarUrl)
                renderNickname(account.nickname)
                renderGender(account.gender)
                renderBalance(account.balance)
            }
        }
    }

    private fun loadAccountSuccess(accountEntity: AccountEntity) {}

    inner class LoadAccountObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            loadAccountSuccess(t)
        }
    }
}