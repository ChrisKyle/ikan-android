package me.chriskyle.ikan.presentation.module.feed.watchhistory

import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface WatchHistoryPresenter : MvpLcePresenter<WatchHistoryView> {

    fun loadWatchHistories()
}