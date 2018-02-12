package me.chriskyle.ikan.presentation.module.notification

import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface NotificationPresenter : MvpLcePresenter<NotificationView> {

    fun loadNotifications()

    fun notificationDetail(notification: NotificationEntity)

    fun deleteNotification(notification: NotificationEntity)
}