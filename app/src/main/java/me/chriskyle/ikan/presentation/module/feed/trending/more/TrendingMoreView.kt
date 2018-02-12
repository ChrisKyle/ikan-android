package me.chriskyle.ikan.presentation.module.feed.trending.more

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface TrendingMoreView : MvpLceView {

    fun renderFeeds(feedEntities: List<FeedEntity>)
}