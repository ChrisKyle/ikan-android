package me.chriskyle.ikan.presentation.module.feed.trending

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.entity.TrendingFeedEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class TrendingPresenterImpl(private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<TendingView>(), TrendingPresenter {

    private var trendingFeeds: MutableList<TrendingFeedEntity> = mutableListOf()
    private var currentPage: Int = 1

    override fun feedSectionClick(section: String) {
        Router.routeToTrendingMore(view?.ctx, section)
    }

    override fun feedItemClick(position: Int, feed: FeedEntity) {
        Router.routeToFeedDetail(view?.ctx, feed)
    }

    override fun loadTrendingFeeds() {
        view?.showLoadingView()

        currentPage = 1
        val feedParamProvider = FeedParamProvider()
        feedParamProvider.page(currentPage)

        feedRepositoryManager.executeGetTrendingFeeds(feedParamProvider, LoadTrendingFeedsObserver())
    }

    override fun refresh() {
        currentPage = 1
        val feedParamProvider = FeedParamProvider()
        feedParamProvider.page(currentPage)

        feedRepositoryManager.executeGetTrendingFeeds(FeedParamProvider(), RefreshTrendingFeedsObserver())
    }

    override fun loadmore() {
        val feedParamProvider = FeedParamProvider()
        feedParamProvider.page(++currentPage)
        feedRepositoryManager.executeGetTrendingFeeds(FeedParamProvider(), LoadmoreTrendingFeedsObserver())
    }

    override fun reload() {
        loadTrendingFeeds()
    }

    private fun loadTrendingFeedsSuccess(pageTrendingFeed: PagerEntity<List<TrendingFeedEntity>>) {
        view?.showRefreshCompleted()

        if (pageTrendingFeed.list == null || pageTrendingFeed.list!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            trendingFeeds = pageTrendingFeed.list as MutableList<TrendingFeedEntity>

            view?.showContent()
            view?.renderTrendingFeeds(trendingFeeds)

            if (pageTrendingFeed.total == trendingFeeds.size) {
                view?.showNoMoreData()
            }
        }
    }

    private fun loadTrendingFeedsError(e: Throwable) {
        view?.let {
            view!!.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private fun refreshTrendingFeedsSuccess(pageTrendingFeed: PagerEntity<List<TrendingFeedEntity>>) {
        view?.showRefreshCompleted()

        if (pageTrendingFeed.list == null || pageTrendingFeed.list!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            trendingFeeds = pageTrendingFeed.list as MutableList<TrendingFeedEntity>

            view?.showContent()
            view?.renderTrendingFeeds(trendingFeeds)

            if (pageTrendingFeed.total == trendingFeeds.size) {
                view?.showNoMoreData()
            }
        }
    }

    private fun refreshTrendingFeedsError(e: Throwable) {
        view?.let {
            view!!.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private fun loadmoreTrendingFeedsSuccess(pageTrendingFeed: PagerEntity<List<TrendingFeedEntity>>) {
        view?.let {
            view!!.showLoadMoreCompleted()
            view!!.showContent()

            if (pageTrendingFeed.list!!.isEmpty()) {
                view!!.showEmptyView()
            } else {
                trendingFeeds.addAll(pageTrendingFeed.list!!)
                view!!.renderTrendingFeeds(trendingFeeds)
            }

            if (pageTrendingFeed.total == trendingFeeds.size) {
                view!!.showNoMoreData()
            }
        }
    }

    private fun loadmoreTrendingFeedsError(e: Throwable) {
        view?.let {
            view!!.showLoadMoreCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private inner class LoadTrendingFeedsObserver : DefaultObserver<PagerEntity<List<TrendingFeedEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<TrendingFeedEntity>>) {
            loadTrendingFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadTrendingFeedsError(e)
        }
    }

    private inner class RefreshTrendingFeedsObserver : DefaultObserver<PagerEntity<List<TrendingFeedEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<TrendingFeedEntity>>) {
            refreshTrendingFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            refreshTrendingFeedsError(e)
        }
    }

    private inner class LoadmoreTrendingFeedsObserver : DefaultObserver<PagerEntity<List<TrendingFeedEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<TrendingFeedEntity>>) {
            loadmoreTrendingFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadmoreTrendingFeedsError(e)
        }
    }
}