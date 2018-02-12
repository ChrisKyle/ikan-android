package me.chriskyle.ikan.presentation.module.notification

import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface NotificationView : MvpLceView {

    fun renderNotifications(notifications: List<NotificationEntity>)
}