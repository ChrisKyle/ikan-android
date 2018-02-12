package me.chriskyle.ikan.presentation.module.feed.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.drawee.view.SimpleDraweeView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.CommentEntity
import me.chriskyle.library.toolkit.utils.DateUtils

/**
 * Description :
 *
 * Created by Chris Kyle on 2018/1/28.
 */
class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var commentEntities: MutableList<CommentEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_feed_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = commentEntities.size

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        val commentEntity = commentEntities[position]
        holder.content?.text = commentEntity.content
        holder.nickname?.text = commentEntity.account.nickname
        holder.time?.text = DateUtils.getTimeOral(commentEntity.createTime)
        holder.avatar?.setImageURI(commentEntity.account.avatarUrl)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.avatar)
        @JvmField
        var avatar: SimpleDraweeView? = null

        @BindView(R.id.nickname)
        @JvmField
        var nickname: TextView? = null

        @BindView(R.id.content)
        @JvmField
        var content: TextView? = null

        @BindView(R.id.time)
        @JvmField
        var time: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}