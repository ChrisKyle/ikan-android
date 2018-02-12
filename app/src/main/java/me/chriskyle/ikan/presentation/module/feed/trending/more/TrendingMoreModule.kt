package me.chriskyle.ikan.presentation.module.feed.trending.more

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class TrendingMoreModule {

    @Provides
    fun provideTrendingMorePresenter(feedRepositoryManager: FeedRepositoryManager):
            TrendingMorePresenter = TrendingMorePresenterImpl(feedRepositoryManager)
}