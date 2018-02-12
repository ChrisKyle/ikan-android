package me.chriskyle.ikan.presentation.module.feed.recommend

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface RecommendPresenter : MvpLcePresenter<RecommendView> {

    fun loadFeeds()

    fun feedItemClick(position: Int, feed: FeedEntity)
}