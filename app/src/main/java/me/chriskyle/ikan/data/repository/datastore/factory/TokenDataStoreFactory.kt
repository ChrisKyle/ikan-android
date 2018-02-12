package me.chriskyle.ikan.data.repository.datastore.factory

import me.chriskyle.ikan.data.repository.datastore.TokenDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.CloudTokenDataStore
import me.chriskyle.ikan.data.repository.datastore.local.LocalTokenDataStore
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.library.net.connection.Connector
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
@Singleton
class TokenDataStoreFactory @Inject
internal constructor(private val apiConnector: Connector, private val tokenCache: TokenCache) {

    fun createLocalDataStore(): TokenDataStore = LocalTokenDataStore(tokenCache)

    fun createCloudDataStore(): TokenDataStore = CloudTokenDataStore(apiConnector, tokenCache)
}
