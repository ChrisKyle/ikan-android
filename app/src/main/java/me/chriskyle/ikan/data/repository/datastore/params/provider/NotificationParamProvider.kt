package me.chriskyle.ikan.data.repository.datastore.params.provider

import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.param.BaseParamsProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
class NotificationParamProvider : BaseParamsProvider() {

    fun notificationId(notificationId: Long): NotificationParamProvider {
        append(NOTIFICATION_ID, notificationId)
        return this
    }

    fun getNotificationId() = optionalParam.map[NOTIFICATION_ID] as String

    companion object {

        const val NOTIFICATION_ID = Constants.API.NOTIFICATION_ID
    }
}