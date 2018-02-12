package me.chriskyle.ikan.presentation.module.setting

import android.content.Context
import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.module.notification.NotificationManager
import me.chriskyle.library.eventbus.Bus
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class SettingModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideSettingManager(): NotificationManager = NotificationManager(context)

    @Provides
    fun provideSettingPresenter(bus: Bus,
                                notificationManager: NotificationManager,
                                feedRepositoryManager: FeedRepositoryManager,
                                accountRepositoryManager: AccountRepositoryManager):
            SettingPresenter = SettingPresenterImpl(bus, notificationManager, feedRepositoryManager, accountRepositoryManager)
}