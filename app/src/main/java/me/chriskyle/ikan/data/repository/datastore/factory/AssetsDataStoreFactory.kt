package me.chriskyle.ikan.data.repository.datastore.factory

import me.chriskyle.ikan.data.repository.datastore.cloud.CloudAssetsDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.library.net.connection.Connector
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
@Singleton
class AssetsDataStoreFactory @Inject internal constructor(private val apiConnector: Connector,
                                                          private val tokenInterceptor: TokenInterceptor) {

    fun createCloudDataStore() = CloudAssetsDataStore(apiConnector, tokenInterceptor)
}