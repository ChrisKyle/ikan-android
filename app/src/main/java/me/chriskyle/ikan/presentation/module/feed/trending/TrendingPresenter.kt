package me.chriskyle.ikan.presentation.module.feed.trending

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface TrendingPresenter : MvpLcePresenter<TendingView> {

    fun loadTrendingFeeds()

    fun feedSectionClick(section: String)

    fun feedItemClick(position: Int, feed: FeedEntity)
}