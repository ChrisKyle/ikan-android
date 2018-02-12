package me.chriskyle.ikan.presentation.module.main

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.FuncRepositoryManager
import me.chriskyle.ikan.presentation.event.AccountModifyEvent
import me.chriskyle.ikan.presentation.event.LoginEvent
import me.chriskyle.ikan.presentation.event.LogoutEvent
import me.chriskyle.ikan.presentation.event.ReLoginEvent
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus
import me.chriskyle.library.toolkit.utils.ManifestUtils

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class MainPresenterImpl(private val bus: Bus,
                        private val accountRepositoryManager: AccountRepositoryManager,
                        private val funcRepositoryManager: FuncRepositoryManager) :
        BasePresenter<MainView>(), MainPresenter {

    override fun onViewAttached() {
        super.onViewAttached()
        bus.subscribe(this, bus.obtainSubscriber(ReLoginEvent::class.java, Consumer { event ->
            onReLoginEvent(event)
        }))
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
        view?.renderAvatar(account?.avatarUrl)
    }

    override fun checkVersionUpdate() {
        funcRepositoryManager.executeGetNewestVersion(CheckVersionUpdateObserver())
    }

    override fun search() {
        view?.showSearchFragment()
    }

    override fun account() {
        view?.showAccountFragment()
    }

    private fun onReLoginEvent(event: ReLoginEvent) {
    }

    private fun onLoginEvent(event: LoginEvent) {
        view?.renderAvatar(event.accountEntity.avatarUrl)
    }

    private fun onLogoutEvent(event: LogoutEvent) {
        view?.renderAvatar(null)
    }

    private fun onAccountModifyEvent(event: AccountModifyEvent) {
        event.account?.let {
            view?.renderAvatar(event.account?.avatarUrl)
        }
    }

    private fun checkVersionDateSuccess(versionEntity: VersionEntity) {
        view?.let {
            val currentVersionCode = ManifestUtils.getVersionCode(view!!.ctx)
            if (versionEntity.code > currentVersionCode) {
                view?.showUpdateDialog(versionEntity)
            }
        }
    }

    private inner class CheckVersionUpdateObserver : DefaultObserver<VersionEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: VersionEntity) {
            checkVersionDateSuccess(t)
        }
    }
}