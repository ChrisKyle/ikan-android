package me.chriskyle.ikan.presentation.module.feed.detail

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class FeedDetailModule {

    @Provides
    fun provideFeedDetailPresenter(bus: Bus, accountRepositoryManager: AccountRepositoryManager,
                                   feedRepositoryManager: FeedRepositoryManager):
            FeedDetailPresenter = FeedDetailPresenterImpl(bus, accountRepositoryManager, feedRepositoryManager)
}