package me.chriskyle.ikan.presentation.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.data.repository.datastore.local.cache.AccountCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.FeedCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.impl.AccountCacheImpl
import me.chriskyle.ikan.data.repository.datastore.local.cache.impl.FeedCacheImpl
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import me.chriskyle.ikan.domain.executor.impl.DefaultExecutor
import me.chriskyle.ikan.domain.executor.impl.UIThread
import me.chriskyle.ikan.domain.repository.manager.*
import me.chriskyle.ikan.presentation.di.ApplicationContext
import me.chriskyle.ikan.presentation.module.notification.NotificationManager
import me.chriskyle.library.eventbus.Bus
import me.chriskyle.library.eventbus.BusProvider
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@Module(includes = [NetworkModule::class])
class ApplicationModule(private val application: Application) {

    @Provides
    internal fun provideApplication() = application

    @Provides
    @ApplicationContext
    internal fun provideContext() = application.applicationContext

    @Singleton
    @Provides
    internal fun provideEventBus(): Bus = BusProvider.instance

    @Singleton
    @Provides
    internal fun provideThreadExecutor(): ThreadExecutor = DefaultExecutor()

    @Singleton
    @Provides
    internal fun provideUiThread(): PostExecutionThread = UIThread()

    @Singleton
    @Provides
    internal fun provideNotificationManager(manager: NotificationManager) = manager

    @Singleton
    @Provides
    internal fun provideFeedRepositoryManager(manager: FeedRepositoryManager) = manager

    @Singleton
    @Provides
    internal fun provideNotificationRepositoryManager(manager: NotificationRepositoryManager) = manager

    @Singleton
    @Provides
    internal fun provideUpdateRepositoryManager(manager: UpdateRepositoryManager) = manager

    @Singleton
    @Provides
    internal fun provideAccountRepositoryManager(manager: AccountRepositoryManager) = manager

    @Singleton
    @Provides
    internal fun provideAssetsRepositoryManager(manager: AssetsRepositoryManager) = manager

    @Singleton
    @Provides
    internal fun provideHFBRepositoryManager(manager: FuncRepositoryManager) = manager

    @Singleton
    @Provides
    internal fun provideFeedCache(): FeedCache = FeedCacheImpl.getInstance(application)

    @Singleton
    @Provides
    internal fun provideAccountCache(): AccountCache = AccountCacheImpl.getInstance(application)
}
