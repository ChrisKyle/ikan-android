package me.chriskyle.ikan.presentation.module.notification

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.NotificationRepositoryManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class NotificationModule {

    @Provides
    fun provideNotificationPresenter(notificationRepositoryManager: NotificationRepositoryManager):
            NotificationPresenter = NotificationPresenterImpl(notificationRepositoryManager)
}