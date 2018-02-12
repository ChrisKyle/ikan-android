package me.chriskyle.ikan.presentation.module.feed

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class FeedModule {

    @Provides
    fun provideMovieFeedPresenter(bus: Bus, feedRepositoryManager: FeedRepositoryManager):
            FeedPresenter = FeedPresenterImpl(bus, feedRepositoryManager)
}