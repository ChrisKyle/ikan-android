package me.chriskyle.ikan.presentation.module.feed

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface FeedView : MvpLceView {

    fun renderFeeds(feeds: List<FeedEntity>)
}