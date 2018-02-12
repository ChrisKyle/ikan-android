package me.chriskyle.ikan.presentation.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.data.repository.datastore.cloud.connection.DefaultApiConnector
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.NetworkRequestInterceptor
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.impl.TokenCacheImpl
import me.chriskyle.library.net.connection.Connector
import okhttp3.Interceptor
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@Module
class NetworkModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun provideTokenCache(): TokenCache = TokenCacheImpl.getInstance(context)

    @Singleton
    @Provides
    internal fun provideNetWorkInterceptor(tokenCache: TokenCache): Interceptor
            = NetworkRequestInterceptor(context, tokenCache)

    @Singleton
    @Provides
    internal fun provideConnector(interceptor: Interceptor): Connector = DefaultApiConnector(interceptor)
}