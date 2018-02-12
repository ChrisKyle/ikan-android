package me.chriskyle.ikan.presentation.module.setting

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.repository.datastore.cloud.WebStatic
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.event.LogoutEvent
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.ikan.presentation.module.notification.NotificationManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class SettingPresenterImpl(private val bus: Bus,
                           private val notificationManager: NotificationManager,
                           private val feedRepositoryManager: FeedRepositoryManager,
                           private val accountRepositoryManager: AccountRepositoryManager) :
        BasePresenter<SettingView>(), SettingPresenter {

    override fun checkAccountStatus() {
        if (accountRepositoryManager.executeGetLogged() == null) {
            view?.showUnLoginStatus()
        } else {
            view?.showLoggedStatus()
        }
    }

    override fun checkNotificationStatus() {
        view?.renderNotificationStatus(notificationManager.isOpen())
    }

    override fun openNotification() {
        notificationManager.openNotification()
    }

    override fun closeNotification() {
        notificationManager.closeNotification()
    }

    override fun accountBind() {
        Router.routeToAccountBind(view?.ctx)
    }

    override fun clearDownloadHistory() {
        view?.showClearDownloadHistoryDialog()
    }

    override fun confirmClearDownloadHistory() {
    }

    override fun clearPlayHistory() {
        view?.showClearPlayHistoryDialog()
    }

    override fun confirmClearPlayHistory() {
        feedRepositoryManager.executeClearWatchHistory(ClearWatchHistoryObserver())
    }

    override fun confirmLogout() {
        accountRepositoryManager.executeLogout()
        view?.showUnLoginStatus()
        view?.showLogoutSuccess()
        bus.post(LogoutEvent())
    }

    override fun help() {
        Router.routeToHelp(view?.ctx)
    }

    override fun privacyPolicy() {
        Router.routeToSimpleWeb(view?.ctx, WebStatic.PRIVACY_POLICY,
                view!!.ctx.getString(R.string.setting_privacy_policy))
    }

    override fun about() {
        Router.routeToAbout(view?.ctx)
    }

    override fun logout() {
        view?.showLogoutDialog()
    }

    inner class ClearWatchHistoryObserver : SingleObserver<Long> {

        override fun onSuccess(t: Long) {
            view?.showClearWatchHistorySuccess()
        }

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onError(e: Throwable) {
        }
    }
}