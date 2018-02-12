package me.chriskyle.ikan.presentation.module.feed.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.drawee.view.SimpleDraweeView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.feed.OnFeedItemClickListener
import me.chriskyle.library.toolkit.utils.DateUtils
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/08.
 */
class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var onFeedItemClickListener: OnFeedItemClickListener? = null

    private var feedEntities: MutableList<FeedEntity>

    init {
        feedEntities = mutableListOf()
    }

    override fun getItemCount() = feedEntities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = feedEntities[position]
        holder.thumbnail?.setImageURI(feed.thumbnail)
        holder.title?.text = feed.title
        holder.synopsis?.text = feed.synopsis
        val time = DateUtils.getMinute(feed.duration).toString() +
                holder.itemView.context.getString(R.string.minute)
        holder.time?.text = time

        holder.itemView.setOnClickListener {
            onFeedItemClickListener?.onFeedItemClick(position, feed)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_search_feed, parent, false))

    fun setFeeds(feedEntities: List<FeedEntity>) {
        this.feedEntities = feedEntities as MutableList
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.thumbnail)
        @JvmField
        var thumbnail: SimpleDraweeView? = null

        @BindView(R.id.title)
        @JvmField
        var title: TextView? = null

        @BindView(R.id.synopsis)
        @JvmField
        var synopsis: TextView? = null

        @BindView(R.id.time)
        @JvmField
        var time: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}