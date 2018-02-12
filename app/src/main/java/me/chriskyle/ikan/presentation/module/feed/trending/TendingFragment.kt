package me.chriskyle.ikan.presentation.module.feed.trending

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.data.entity.TrendingFeedEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceFragment
import me.chriskyle.ikan.presentation.module.feed.FeedItemDecoration
import me.chriskyle.ikan.presentation.module.feed.OnFeedItemClickListener
import me.chriskyle.library.support.recyclerview.sectionedadapter.SectionedRecyclerViewAdapter
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class TendingFragment : LceFragment<TendingView, TrendingPresenter>(), TendingView, OnFeedItemClickListener,
        OnFeedSeeMoreClickListener {

    @Inject
    override lateinit var presenter: TrendingPresenter

    private lateinit var sectionedAdapter: SectionedRecyclerViewAdapter

    override val layout: Int
        get() = R.layout.fragment_trending

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadTrendingFeeds()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().trendingComponent(TrendingModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        sectionedAdapter = SectionedRecyclerViewAdapter()
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int {
                return if (sectionedAdapter.getSectionItemViewType(position) ==
                        SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER) 2 else 1
            }
        }
        recyclerView?.layoutManager = layoutManager
        recyclerView?.addItemDecoration(FeedItemDecoration(7))
        recyclerView?.adapter = sectionedAdapter
    }

    override fun renderTrendingFeeds(trendingFeeds: List<TrendingFeedEntity>) {
        sectionedAdapter.removeAllSections()
        trendingFeeds
                .filter { it.feeds!!.isNotEmpty() }
                .forEach {
                    sectionedAdapter.addSection(FeedSection(it.section, it.feeds,
                            this, this))
                }
    }

    override fun onFeedSeeMoreClick(section: String) {
        presenter.feedSectionClick(section)
    }

    override fun onFeedItemClick(position: Int, feed: FeedEntity) {
        presenter.feedItemClick(position, feed)
    }
}