package me.chriskyle.ikan.presentation.module.feed

import android.os.Bundle
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
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class FeedPresenterImpl(private val bus: Bus, private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<FeedView>(), FeedPresenter {

    private var feeds: MutableList<FeedEntity> = mutableListOf()
    private lateinit var segment: String
    private var currentPage: Int = 1

    override fun dispatchArguments(arguments: Bundle?) {
        arguments?.let {
            segment = arguments.getString(FeedFragment.BUNDLE_KEY_SEGMENT)
            loadFeeds()
        }
    }

    private fun loadFeeds() {
        view?.showLoadingView()

        currentPage = 1
        val paramProvider = FeedParamProvider()
        paramProvider.page(currentPage)
        paramProvider.segment(segment)
        feedRepositoryManager.executeGetHomeFeeds(paramProvider, LoadFeedsObserver())
    }

    override fun refresh() {
        currentPage = 1
        val paramProvider = FeedParamProvider()
        paramProvider.page(currentPage)
        paramProvider.segment(segment)
        feedRepositoryManager.executeGetHomeFeeds(paramProvider, RefreshFeedsObserver())
    }

    override fun loadmore() {
        val paramProvider = FeedParamProvider()
        paramProvider.page(++currentPage)
        paramProvider.segment(segment)
        feedRepositoryManager.executeGetHomeFeeds(paramProvider, LoadmoreFeedsObserver())
    }

    override fun reload() {
        loadFeeds()
    }

    override fun feedItemClick(position: Int, feed: FeedEntity) {
        Router.routeToFeedDetail(view?.ctx, feed)
    }

    private fun loadFeedsSuccess(pageFeed: PagerEntity<List<FeedEntity>>) {
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

    private fun loadFeedsError(e: Throwable) {
        view?.let {
            view?.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
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
            view!!.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private fun loadmoreFeedsSuccess(pageFeed: PagerEntity<List<FeedEntity>>) {
        view?.let {
            view!!.showLoadMoreCompleted()
            view!!.showContent()

            if (pageFeed.list!!.isEmpty()) {
                view!!.showEmptyView()
            } else {
                feeds.addAll(pageFeed.list!!)
                view!!.renderFeeds(feeds)
            }

            if (pageFeed.total == feeds.size) {
                view!!.showNoMoreData()
            }
        }
    }

    private fun loadmoreFeedsError(e: Throwable) {
        view?.let {
            view!!.showLoadMoreCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private inner class LoadFeedsObserver : DefaultObserver<PagerEntity<List<FeedEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<FeedEntity>>) {
            loadFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadFeedsError(e)
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