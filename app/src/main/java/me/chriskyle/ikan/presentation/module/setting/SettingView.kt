package me.chriskyle.ikan.presentation.module.setting

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface SettingView : MvpView {

    fun renderNotificationStatus(isOpen: Boolean)

    fun showClearDownloadHistoryDialog()

    fun showClearPlayHistoryDialog()

    fun showLogoutDialog()

    fun showLoggedStatus()

    fun showUnLoginStatus()

    fun showClearWatchHistorySuccess()

    fun showLogoutSuccess()
}