package me.chriskyle.ikan.presentation.module.video

import android.content.Intent
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class VideoPlayerPresenterImpl : BasePresenter<VideoPlayerView>(), VideoPlayerPresenter {

    private lateinit var feed: FeedEntity

    override fun dispatchIntent(intent: Intent?) {
        intent?.let {
            feed = intent.getParcelableExtra(VideoPlayerActivity.INTENT_EXTRA_KEY_FEED)
            view?.renderDownloadFeed(feed)
        }
    }

    override fun refresh() {
    }

    override fun loadmore() {
    }

    override fun reload() {
    }
}