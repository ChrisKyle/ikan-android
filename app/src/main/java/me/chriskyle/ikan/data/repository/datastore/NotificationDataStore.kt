package me.chriskyle.ikan.data.repository.datastore

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.NotificationParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
interface NotificationDataStore {

    fun getNotifications(notificationParamProvider: NotificationParamProvider): Observable<PagerEntity<List<NotificationEntity>>>

    fun deleteNotification(notificationParamProvider: NotificationParamProvider) : Observable<NotificationEntity>
}
