package me.chriskyle.ikan.presentation.module.feed.discover

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.feed.FeedAdapter
import me.chriskyle.ikan.presentation.module.feed.FeedViewHolder
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class DiscoverFeedAdapter @Inject constructor() : FeedAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FeedViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_discover_movie_feed, parent, false))
    }
}
