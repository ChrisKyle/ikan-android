package me.chriskyle.ikan.presentation.module.feed.trending.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceActivity
import me.chriskyle.ikan.presentation.module.feed.FeedAdapter
import me.chriskyle.ikan.presentation.module.feed.OnFeedItemClickListener
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class TrendingMoreActivity : LceActivity<TrendingMoreView, TrendingMorePresenter>(), TrendingMoreView
        , OnFeedItemClickListener {

    @Inject
    lateinit var feedAdapter: FeedAdapter
    @Inject
    override lateinit var presenter: TrendingMorePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.dispatchIntent(intent)
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().trendingMoreComponent(TrendingMoreModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_trending_more)

        recyclerView?.layoutManager = GridLayoutManager(this, 2)
        recyclerView?.adapter = feedAdapter
        feedAdapter.onFeedItemClickListener = this
    }

    override fun renderFeeds(feedEntities: List<FeedEntity>) {
        feedAdapter.setFeeds(feedEntities)
    }

    override fun onFeedItemClick(position: Int, feed: FeedEntity) {
        presenter.feedItemClick(position, feed)
    }

    companion object {

        const val EXTRA_KEY_CATEGORY = "category"

        fun getCallingIntent(context: Context, category: String): Intent {
            val intent = Intent(context, TrendingMoreActivity::class.java)
            intent.putExtra(EXTRA_KEY_CATEGORY, category)
            return intent
        }
    }
}