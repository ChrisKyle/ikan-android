package me.chriskyle.ikan.presentation.module.feed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/14.
 */
open class FeedAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onFeedItemClickListener: OnFeedItemClickListener? = null
    var feedEntities: MutableList<FeedEntity>

    init {
        feedEntities = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val feed: FeedEntity = feedEntities[position]

        val feedViewHolder: FeedViewHolder = viewHolder as FeedViewHolder

        feedViewHolder.thumbnail?.setImageURI(feed.thumbnail)
        feedViewHolder.title?.text = feed.title
        feedViewHolder.synopsis?.text = feed.synopsis

        feedViewHolder.itemView.setOnClickListener {
            onFeedItemClickListener?.onFeedItemClick(position, feed)
        }
    }

    override fun getItemCount() = feedEntities.size

    open fun setFeeds(feedEntities: List<FeedEntity>) {
        this.feedEntities = feedEntities as MutableList
        this.notifyDataSetChanged()
    }
}
