package me.chriskyle.ikan.presentation.module.feed

import me.chriskyle.ikan.data.entity.FeedEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/19.
 */
interface OnFeedItemClickListener {

    fun onFeedItemClick(position: Int, feed: FeedEntity)
}
