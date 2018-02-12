package me.chriskyle.ikan.presentation.module.feed.recommend

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.TextView
import butterknife.BindView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceFragment
import me.chriskyle.ikan.presentation.module.feed.FeedAdapter
import me.chriskyle.ikan.presentation.module.feed.OnFeedItemClickListener
import me.chriskyle.library.support.recyclerview.itemdecoration.DotItemDecoration
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class RecommendFragment : LceFragment<RecommendView, RecommendPresenter>(), RecommendView,
        OnFeedItemClickListener {

    @BindView(R.id.title)
    @JvmField
    var title: TextView? = null

    @Inject
    override lateinit var presenter: RecommendPresenter
    @Inject
    lateinit var feedAdapter: FeedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadFeeds()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().seeListComponent(RecommendModule()).inject(this)
    }

    override val layout: Int
        get() = R.layout.fragment_recommend

    override fun initView() {
        super.initView()

        val itemDecoration = DotItemDecoration.Builder(activity)
                .setOrientation(DotItemDecoration.VERTICAL)
                .setItemStyle(DotItemDecoration.STYLE_DRAW)
                .setTopDistance(10f)
                .setItemInterVal(20f)
                .setItemPaddingLeft(5f)
                .setItemPaddingRight(5f)
                .setDotColor(Color.WHITE)
                .setDotRadius(2)
                .setDotPaddingTop(0)
                .setDotInItemOrientationCenter(false)
                .setLineColor(ContextCompat.getColor(activity!!, R.color.primary_dark))
                .setLineWidth(1f)
                .create()
        recyclerView?.addItemDecoration(itemDecoration)
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.adapter = feedAdapter
        feedAdapter.onFeedItemClickListener = this
        refreshLayout?.isEnableLoadmore = false
    }

    override fun renderTitle(title: String) {
        this.title?.text = title
    }

    override fun renderFeeds(feeds: List<FeedEntity>) {
        feedAdapter.setFeeds(feeds)
    }

    override fun onFeedItemClick(position: Int, feed: FeedEntity) {
        presenter.feedItemClick(position, feed)
    }
}