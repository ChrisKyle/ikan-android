package me.chriskyle.ikan.presentation.module.feed.trending

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import me.chriskyle.ikan.R

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/17.
 */
class FeedSectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.section)
    @JvmField
    var section: TextView? = null

    @BindView(R.id.look_more)
    @JvmField
    var lookMore: TextView? = null

    init {
        ButterKnife.bind(this, itemView)
    }
}