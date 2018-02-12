package me.chriskyle.ikan.presentation.module.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class NotificationActivity : LceActivity<NotificationView, NotificationPresenter>(), NotificationView,
        NotificationAdapter.OnItemClickListener, OnDeleteClickListener {

    @Inject
    lateinit var notificationAdapter: NotificationAdapter
    @Inject
    override lateinit var presenter: NotificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadNotifications()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().notificationComponent(NotificationModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_notification)

        notificationAdapter.onItemClickListener = this
        notificationAdapter.onDeleteClickListener = this
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = notificationAdapter
    }

    override fun renderNotifications(notifications: List<NotificationEntity>) {
        notificationAdapter.setNotifications(notifications)
    }

    override fun onItemClick(notification: NotificationEntity) {
        presenter.notificationDetail(notification)
    }

    override fun onDeleteClick(notificationEntity: NotificationEntity) {
        presenter.deleteNotification(notificationEntity)
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, NotificationActivity::class.java)
    }
}