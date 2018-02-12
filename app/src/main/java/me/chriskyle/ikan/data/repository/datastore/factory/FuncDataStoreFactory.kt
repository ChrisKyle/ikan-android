package me.chriskyle.ikan.data.repository.datastore.factory

import me.chriskyle.ikan.data.repository.datastore.FuncDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.CloudFuncDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.library.net.connection.Connector
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
@Singleton
class FuncDataStoreFactory @Inject internal constructor(private val apiConnector: Connector,
                                                        private val tokenInterceptor: TokenInterceptor) {

    fun createCloudDataStore(): FuncDataStore = CloudFuncDataStore(apiConnector, tokenInterceptor)
}