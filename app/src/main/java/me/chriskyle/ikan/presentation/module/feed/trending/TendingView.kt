package me.chriskyle.ikan.presentation.module.feed.trending

import me.chriskyle.ikan.data.entity.TrendingFeedEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface TendingView : MvpLceView {

    fun renderTrendingFeeds(trendingFeeds: List<TrendingFeedEntity>)
}