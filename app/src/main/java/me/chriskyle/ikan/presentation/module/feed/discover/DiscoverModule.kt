package me.chriskyle.ikan.presentation.module.feed.discover

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class DiscoverModule {

    @Provides
    fun provideDiscoverPresenter(feedRepositoryManager: FeedRepositoryManager):
            DiscoverPresenter = DiscoverPresenterImpl(feedRepositoryManager)
}