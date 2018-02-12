package me.chriskyle.ikan.data.repository

import me.chriskyle.ikan.data.repository.datastore.factory.NotificationDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.NotificationParamProvider
import me.chriskyle.ikan.domain.repository.NotificationRepository
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
class NotificationDataRepository @Inject constructor(private var notificationDataStoreFactory: NotificationDataStoreFactory) :
        NotificationRepository {

    override fun getNotifications(notificationParamProvider: NotificationParamProvider) = notificationDataStoreFactory
            .createCloudDataStore()
            .getNotifications(notificationParamProvider)

    override fun deleteNotification(notificationParamProvider: NotificationParamProvider) = notificationDataStoreFactory
            .createCloudDataStore()
            .deleteNotification(notificationParamProvider)
}