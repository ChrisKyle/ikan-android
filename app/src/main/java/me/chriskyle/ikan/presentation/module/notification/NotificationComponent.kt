package me.chriskyle.ikan.presentation.module.notification

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [NotificationModule::class])
interface NotificationComponent {

    fun inject(notificationActivity:  NotificationActivity)
}