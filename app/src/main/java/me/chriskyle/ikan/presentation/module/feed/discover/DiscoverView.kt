package me.chriskyle.ikan.presentation.module.feed.discover

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface DiscoverView : MvpView {

    fun renderFeeds(feeds: List<FeedEntity>)

    fun showContent()

    fun showLoadingView()

    fun showEmptyView()

    fun showErrorView(errorMsg: String?)

    fun showStatusMsg(errorMsg: String)
}