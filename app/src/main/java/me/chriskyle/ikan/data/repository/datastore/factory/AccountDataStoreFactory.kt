package me.chriskyle.ikan.data.repository.datastore.factory

import me.chriskyle.ikan.data.repository.datastore.cloud.CloudAccountDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.local.LocalAccountDataStore
import me.chriskyle.ikan.data.repository.datastore.local.cache.AccountCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.library.net.connection.Connector
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
@Singleton
class AccountDataStoreFactory @Inject constructor(private val apiConnector: Connector,
                                                  private val tokenInterceptor: TokenInterceptor,
                                                  private val accountCache: AccountCache,
                                                  private val tokenCache: TokenCache) {

    fun createCloudDataStore() = CloudAccountDataStore(apiConnector, tokenInterceptor, accountCache, tokenCache)

    fun createLocalDataStore() = LocalAccountDataStore(accountCache, tokenCache)
}