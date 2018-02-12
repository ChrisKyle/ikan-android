package me.chriskyle.ikan.presentation.module.feed.watchhistory

import me.chriskyle.ikan.data.entity.WatchHistoryEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface WatchHistoryView : MvpLceView {

    fun renderFeeds(feeds: List<WatchHistoryEntity>)
}