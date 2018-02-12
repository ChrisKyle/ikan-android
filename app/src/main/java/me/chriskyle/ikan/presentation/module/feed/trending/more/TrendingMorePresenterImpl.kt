package me.chriskyle.ikan.presentation.module.feed.trending.more

import android.content.Intent
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.ikan.presentation.module.feed.FeedType
import me.chriskyle.ikan.presentation.module.feed.trending.more.TrendingMoreActivity.Companion.EXTRA_KEY_CATEGORY

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class TrendingMorePresenterImpl(private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<TrendingMoreView>(), TrendingMorePresenter {

    private var feeds: MutableList<FeedEntity> = mutableListOf()
    private var currentPage: Int = 1

    private lateinit var category: String

    override fun dispatchIntent(intent: Intent) {
        intent.let {
            val category = intent.getStringExtra(EXTRA_KEY_CATEGORY)
            loadFeeds(category)
        }
    }

    private fun loadFeeds(category: String) {
        this.category = category

        view?.showLoadingView()

        val paramProvider = FeedParamProvider()
        paramProvider.page(currentPage)
        paramProvider.category(category)

        feedRepositoryManager.executeGetTrendingMoreFeeds(paramProvider, RefreshFeedsObserver())
    }

    override fun refresh() {
        currentPage = 1

        val paramProvider = FeedParamProvider()
        paramProvider.page(currentPage)
        paramProvider.category(category)

        feedRepositoryManager.executeGetTrendingMoreFeeds(paramProvider, RefreshFeedsObserver())
    }

    override fun loadmore() {
        val paramProvider = FeedParamProvider()
        paramProvider.page(++currentPage)
        paramProvider.category(category)
        feedRepositoryManager.executeGetTrendingMoreFeeds(paramProvider, LoadmoreFeedsObserver())
    }

    override fun reload() {
        view?.showLoadingView()

        currentPage = 1

        val paramProvider = FeedParamProvider()
        paramProvider.page(currentPage)
        paramProvider.type(FeedType.TRENDING)

        feedRepositoryManager.executeGetTrendingMoreFeeds(paramProvider, RefreshFeedsObserver())
    }

    override fun feedItemClick(position: Int, feed: FeedEntity) {
        Router.routeToFeedDetail(view?.ctx, feed)
    }

    private fun refreshFeedsSuccess(pageFeed: PagerEntity<List<FeedEntity>>) {
        view?.showRefreshCompleted()

        if (pageFeed.list == null || pageFeed.list!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            feeds = pageFeed.list as MutableList<FeedEntity>

            view?.showContent()
            view?.renderFeeds(feeds)

            if (pageFeed.total == feeds.size) {
                view?.showNoMoreData()
            }
        }
    }

    private fun refreshFeedsError(e: Throwable) {
        view?.let {
            view?.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private fun loadmoreFeedsSuccess(pageFeed: PagerEntity<List<FeedEntity>>) {
        view?.let {
            view?.showLoadMoreCompleted()
            view?.showContent()

            pageFeed.list?.let {
                feeds.addAll(pageFeed.list!!)
                view?.renderFeeds(feeds)
            }

            if (pageFeed.total == feeds.size) {
                view?.showNoMoreData()
            }
        }
    }

    private fun loadmoreFeedsError(e: Throwable) {
        view?.let {
            view!!.showLoadMoreCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private inner class RefreshFeedsObserver : DefaultObserver<PagerEntity<List<FeedEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<FeedEntity>>) {
            refreshFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            refreshFeedsError(e)
        }
    }

    private inner class LoadmoreFeedsObserver : DefaultObserver<PagerEntity<List<FeedEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<FeedEntity>>) {
            loadmoreFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadmoreFeedsError(e)
        }
    }
}