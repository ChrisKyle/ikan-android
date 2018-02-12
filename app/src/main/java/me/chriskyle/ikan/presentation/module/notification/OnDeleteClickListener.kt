package me.chriskyle.ikan.presentation.module.notification

import me.chriskyle.ikan.data.entity.NotificationEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface OnDeleteClickListener {

    fun onDeleteClick(notificationEntity: NotificationEntity)
}
