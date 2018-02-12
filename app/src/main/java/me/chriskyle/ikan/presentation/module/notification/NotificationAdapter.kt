package me.chriskyle.ikan.presentation.module.notification

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.library.support.SwipeMenuLayout
import me.chriskyle.library.toolkit.utils.DateUtils
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/03.
 */
class NotificationAdapter @Inject constructor() : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    lateinit var onDeleteClickListener: OnDeleteClickListener
    private var notifications: MutableList<NotificationEntity> = mutableListOf()

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.title?.text = notification.title
        holder.content?.text = notification.content
        holder.date?.text = DateUtils.getTimeOral(notification.createTime)

        when {
            notification.type == NotificationType.SYSTEM -> {
                (holder.itemView as SwipeMenuLayout).isSwipeEnable = false
                holder.icon?.setImageResource(R.drawable.ic_notification_system)
            }
            notification.type == NotificationType.ACTIVITY -> {
                (holder.itemView as SwipeMenuLayout).isSwipeEnable = false
                holder.icon?.setImageResource(R.drawable.ic_notification_activity)
            }
            notification.type == NotificationType.PERSIONAL -> {
                (holder.itemView as SwipeMenuLayout).isSwipeEnable = true
                holder.icon?.setImageResource(R.drawable.ic_notification_personal)
            }
        }

        holder.contentLay?.setOnClickListener {
            onItemClickListener.onItemClick(notification)
        }
        holder.del?.setOnClickListener {
            onDeleteClickListener.onDeleteClick(notification)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent,
                false))
    }

    fun setNotifications(notifications: List<NotificationEntity>) {
        this.notifications = notifications as MutableList
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.content_lay)
        @JvmField
        var contentLay: View? = null

        @BindView(R.id.del)
        @JvmField
        var del: View? = null

        @BindView(R.id.icon)
        @JvmField
        var icon: ImageView? = null

        @BindView(R.id.title)
        @JvmField
        var title: TextView? = null

        @BindView(R.id.content)
        @JvmField
        var content: TextView? = null

        @BindView(R.id.date)
        @JvmField
        var date: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    interface OnItemClickListener {

        fun onItemClick(notification: NotificationEntity)
    }
}
