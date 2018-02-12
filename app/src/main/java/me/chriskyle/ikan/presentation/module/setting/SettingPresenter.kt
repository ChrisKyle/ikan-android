package me.chriskyle.ikan.presentation.module.setting

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface SettingPresenter : MvpPresenter<SettingView> {

    fun checkAccountStatus()

    fun checkNotificationStatus()

    fun openNotification()

    fun closeNotification()

    fun accountBind()

    fun clearDownloadHistory()

    fun confirmClearDownloadHistory()

    fun clearPlayHistory()

    fun confirmClearPlayHistory()

    fun confirmLogout()

    fun help()

    fun privacyPolicy()

    fun about()

    fun logout()
}