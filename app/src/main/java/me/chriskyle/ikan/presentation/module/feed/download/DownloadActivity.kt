package me.chriskyle.ikan.presentation.module.feed.download

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.lce.LceActivity
import javax.inject.Inject

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/10/27.
 */
class DownloadActivity : LceActivity<DownloadView, DownloadPresenter>(), DownloadView,
        DownloadFeedAdapter.OnFeedDeleteClickListener {

    @Inject
    override lateinit var presenter: DownloadPresenter

    private var downloadFeedAdapter: DownloadFeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadDownloadFeeds()
    }

    override fun onDestroy() {
        presenter.destroyDownloader()
        recyclerView?.layoutManager = null
        recyclerView?.adapter = null
        downloadFeedAdapter = null

        super.onDestroy()
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_download)
        refreshLayout?.isEnableRefresh = false
        recyclerView?.layoutManager = LinearLayoutManager(this)
        downloadFeedAdapter = DownloadFeedAdapter(ctx)
        recyclerView?.adapter = downloadFeedAdapter
        downloadFeedAdapter!!.onFeedDeleteClickListener = this
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().downloadComponent(DownloadModule()).inject(this)
    }

    override fun downloadDataChanged() {
        if (downloadFeedAdapter != null) {
            runOnUiThread {
                if (downloadFeedAdapter != null) {
                    downloadFeedAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onFeedDeleteClick(downloadEntity: DownloadEntity) {
        presenter.deleteHistory(downloadEntity)
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, DownloadActivity::class.java)
    }
}
