package me.chriskyle.ikan.presentation.module.feed

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.base.lazyload.LazyLoadFragment
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class FeedFragment : LazyLoadFragment<FeedView, FeedPresenter>(), FeedView,
        OnFeedItemClickListener {

    @Inject
    override lateinit var presenter: FeedPresenter

    @Inject
    lateinit var feedAdapter: FeedAdapter

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().movieFeedsComponent(FeedModule()).inject(this)
    }

    override val layout: Int
        get() = R.layout.fragment_home_movie_feed

    override fun initView() {
        super.initView()

        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.addItemDecoration(FeedItemDecoration(7))
        recyclerView?.adapter = feedAdapter
        feedAdapter.onFeedItemClickListener = this
    }

    override fun lazyLoad() {
        presenter.dispatchArguments(arguments)
    }

    override fun renderFeeds(feeds: List<FeedEntity>) {
        feedAdapter.setFeeds(feeds)
    }

    override fun onFeedItemClick(position: Int, feed: FeedEntity) {
        presenter.feedItemClick(position, feed)
    }

    companion object {

        const val BUNDLE_KEY_SEGMENT = "segment"
    }
}