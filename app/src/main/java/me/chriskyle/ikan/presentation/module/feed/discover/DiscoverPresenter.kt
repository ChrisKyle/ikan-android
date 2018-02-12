package me.chriskyle.ikan.presentation.module.feed.discover

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface DiscoverPresenter : MvpPresenter<DiscoverView> {

    fun loadFeeds()

    fun reloadFeeds()

    fun refreshFeeds()

    fun feedDetail(feed: FeedEntity)
}