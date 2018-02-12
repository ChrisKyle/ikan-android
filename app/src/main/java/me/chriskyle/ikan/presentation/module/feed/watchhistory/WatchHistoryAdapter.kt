package me.chriskyle.ikan.presentation.module.feed.watchhistory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.drawee.view.SimpleDraweeView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.WatchHistoryEntity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/03.
 */
class WatchHistoryAdapter @Inject constructor() : RecyclerView.Adapter<WatchHistoryAdapter.ViewHolder>() {

    private var watchHistoryEntities: MutableList<WatchHistoryEntity> = mutableListOf()

    var onItemClickListener: OnItemClickListener? = null
    var onDeleteClickListener: OnDeleteClickListener? = null

    override fun getItemCount() = watchHistoryEntities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val watchHistory = watchHistoryEntities[position]
        holder.title?.text = watchHistory.title
        holder.time?.text = watchHistory.time
        holder.thumbnail?.setImageURI(watchHistory.thumbnail)

        holder.contentLay?.setOnClickListener {
            onItemClickListener?.onItemClick(watchHistory)
        }
        holder.delete?.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(watchHistory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_watch_history, parent, false))

    fun setWatchHistories(entities: List<WatchHistoryEntity>) {
        this.watchHistoryEntities = entities as MutableList
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.content_lay)
        @JvmField
        var contentLay: View? = null

        @BindView(R.id.thumbnail)
        @JvmField
        var thumbnail: SimpleDraweeView? = null

        @BindView(R.id.title)
        @JvmField
        var title: TextView? = null

        @BindView(R.id.time)
        @JvmField
        var time: TextView? = null

        @BindView(R.id.watch_duration)
        @JvmField
        var watchDuration: TextView? = null

        @BindView(R.id.delete)
        @JvmField
        var delete: View? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}