package me.chriskyle.ikan.data.repository.datastore.local

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.datastore.NotificationDataStore
import me.chriskyle.ikan.data.repository.datastore.params.provider.NotificationParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
class LocalNotificationDataStore : NotificationDataStore {

    override fun getNotifications(notificationParamProvider: NotificationParamProvider):
            Observable<PagerEntity<List<NotificationEntity>>> {
        throw UnsupportedOperationException()
    }

    override fun deleteNotification(notificationParamProvider: NotificationParamProvider): Observable<NotificationEntity> {
        throw UnsupportedOperationException()
    }
}