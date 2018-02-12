package me.chriskyle.ikan.presentation.module.video

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface VideoPlayerView : MvpLceView {

    fun renderDownloadFeed(feed: FeedEntity)
}