package me.chriskyle.ikan.presentation.module.feed.watchhistory

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [WatchHistoryModule::class])
interface WatchHistoryComponent {

    fun inject(downloadActivity: WatchHistoryActivity)
}