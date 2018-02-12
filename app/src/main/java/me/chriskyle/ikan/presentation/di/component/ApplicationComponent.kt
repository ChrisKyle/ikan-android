package me.chriskyle.ikan.presentation.di.component

import android.app.Application
import android.content.Context
import dagger.Component
import me.chriskyle.ikan.data.repository.datastore.local.cache.AccountCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.FeedCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.presentation.di.ApplicationContext
import me.chriskyle.ikan.presentation.di.module.ApplicationModule
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import me.chriskyle.library.eventbus.Bus
import me.chriskyle.library.net.connection.Connector
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun bus(): Bus

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun connector(): Connector

    fun tokenCache(): TokenCache

    fun feedCache(): FeedCache

    fun accountCache(): AccountCache
}
