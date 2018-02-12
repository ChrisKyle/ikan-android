package me.chriskyle.ikan.presentation.module.feed.trending.more

import android.content.Intent
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface TrendingMorePresenter : MvpLcePresenter<TrendingMoreView> {

    fun dispatchIntent(intent: Intent)

    fun feedItemClick(position: Int, feed: FeedEntity)
}