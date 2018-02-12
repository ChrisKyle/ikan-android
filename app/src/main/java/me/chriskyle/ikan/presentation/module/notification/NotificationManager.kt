package me.chriskyle.ikan.presentation.module.notification

import android.content.Context
import me.chriskyle.library.toolkit.data.PreferenceHelper

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/14.
 */
class NotificationManager constructor(context: Context) {

    private var preference = PreferenceHelper.getInstance(context)

    fun closeNotification() {
        preference.put(IS_OPEN, false).commit()
    }

    fun openNotification() {
        preference.put(IS_OPEN, true).commit()
    }

    fun isOpen() = preference.getBoolean(IS_OPEN, false)

    companion object {

        private val IS_OPEN = "notification_is_open"
    }
}
