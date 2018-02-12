package me.chriskyle.ikan.presentation.module.feed.watchhistory

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.WatchHistoryEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class WatchHistoryActivity : LceActivity<WatchHistoryView, WatchHistoryPresenter>(),
        WatchHistoryView, OnItemClickListener, OnDeleteClickListener {

    @Inject
    lateinit var watchHistoryAdapter: WatchHistoryAdapter
    @Inject
    override lateinit var presenter: WatchHistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadWatchHistories()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().watchHistoryComponent(WatchHistoryModule()).inject(this)
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_watch_history)
        emptyLceView?.setDesText(R.string.watch_history_is_empty)

        refreshLayout?.refreshHeader?.setPrimaryColors(ContextCompat.getColor(this, R.color.content_bg))
        refreshLayout?.isEnableRefresh = false
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = watchHistoryAdapter
        watchHistoryAdapter.onItemClickListener = this
        watchHistoryAdapter.onDeleteClickListener = this
    }

    override fun renderFeeds(feeds: List<WatchHistoryEntity>) {
        watchHistoryAdapter.setWatchHistories(feeds)
    }

    override fun onItemClick(watchHistoryEntity: WatchHistoryEntity) {
    }

    override fun onDeleteClick(watchHistoryEntity: WatchHistoryEntity) {
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, WatchHistoryActivity::class.java)
    }
}