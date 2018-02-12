package me.chriskyle.ikan.data.repository.datastore.factory

import me.chriskyle.ikan.data.repository.datastore.cloud.CloudNotificationDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.library.net.connection.Connector
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
@Singleton
class NotificationDataStoreFactory @Inject internal constructor(private val apiConnector: Connector,
                                                                private val tokenInterceptor: TokenInterceptor) {

    fun createCloudDataStore() = CloudNotificationDataStore(apiConnector, tokenInterceptor)
}