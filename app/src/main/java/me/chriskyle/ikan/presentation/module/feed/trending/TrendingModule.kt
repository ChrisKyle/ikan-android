package me.chriskyle.ikan.presentation.module.feed.trending

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class TrendingModule {

    @Provides
    fun provideTrendingPresenter(feedRepositoryManager: FeedRepositoryManager):
            TrendingPresenter = TrendingPresenterImpl(feedRepositoryManager)
}