package me.chriskyle.ikan.presentation.module.feed

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.drawee.view.SimpleDraweeView
import me.chriskyle.ikan.R

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/17.
 */
class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.thumbnail)
    @JvmField
    var thumbnail: SimpleDraweeView? = null

    @BindView(R.id.title)
    @JvmField
    var title: TextView? = null

    @BindView(R.id.synopsis)
    @JvmField
    var synopsis: TextView? = null

    init {
        ButterKnife.bind(this, itemView)
    }
}