package me.chriskyle.ikan.data.repository.datastore.factory

import me.chriskyle.ikan.data.repository.datastore.FeedDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.CloudFeedDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.local.LocalFeedDataStore
import me.chriskyle.ikan.data.repository.datastore.local.cache.FeedCache
import me.chriskyle.library.net.connection.Connector
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/19.
 */
@Singleton
class FeedDataStoreFactory @Inject internal constructor(private val apiConnector: Connector,
                                                        private val tokenInterceptor: TokenInterceptor,
                                                        private val feedCache: FeedCache) {

    fun createCloudDataStore(): FeedDataStore = CloudFeedDataStore(apiConnector, tokenInterceptor, feedCache)

    fun createLocalDataStore(): FeedDataStore = LocalFeedDataStore(feedCache)
}