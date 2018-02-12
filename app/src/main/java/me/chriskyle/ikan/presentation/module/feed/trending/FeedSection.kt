package me.chriskyle.ikan.presentation.module.feed.trending

import android.support.v7.widget.RecyclerView
import android.view.View

import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.feed.FeedViewHolder
import me.chriskyle.ikan.presentation.module.feed.OnFeedItemClickListener
import me.chriskyle.library.support.recyclerview.sectionedadapter.SectionParameters
import me.chriskyle.library.support.recyclerview.sectionedadapter.StatelessSection

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/17.
 */
class FeedSection internal constructor(private val section: String?, private var feedEntities: List<FeedEntity>?,
                                       private val onFeedSeeMoreClickListener: OnFeedSeeMoreClickListener,
                                       private val onFeedItemClickListener: OnFeedItemClickListener) :
        StatelessSection(SectionParameters.Builder(R.layout.item_feed)
                .headerResourceId(R.layout.layout_feed_section)
                .build()) {

    override fun getItemViewHolder(view: View) = FeedViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feedViewHolder = holder as FeedViewHolder
        val feed: FeedEntity = feedEntities!![position]

        feedViewHolder.thumbnail?.setImageURI(feed.thumbnail)
        feedViewHolder.title?.text = feed.title
        feedViewHolder.synopsis?.text = feed.synopsis

        feedViewHolder.itemView.setOnClickListener {
            onFeedItemClickListener.onFeedItemClick(position, feed)
        }
    }

    override fun getHeaderViewHolder(view: View) = FeedSectionViewHolder(view)

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder = holder as FeedSectionViewHolder
        if (section != null) {
            headerHolder.section?.text = section
            headerHolder.lookMore?.setOnClickListener {
                onFeedSeeMoreClickListener.onFeedSeeMoreClick(section)
            }
        }
    }

    override fun getContentItemsTotal() = feedEntities!!.size
}
