package me.chriskyle.ikan.presentation.module.feed.watchhistory

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class WatchHistoryModule {

    @Provides
    fun provideWatchHistoryPresenter(feedRepositoryManager: FeedRepositoryManager):
            WatchHistoryPresenter = WatchHistoryPresenterImpl(feedRepositoryManager)
}