package me.chriskyle.ikan.presentation.module.feed.recommend

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface RecommendView : MvpLceView {

    fun renderTitle(title : String)

    fun renderFeeds(feeds: List<FeedEntity>)
}