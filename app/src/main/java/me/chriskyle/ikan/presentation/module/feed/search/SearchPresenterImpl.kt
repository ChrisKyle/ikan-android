package me.chriskyle.ikan.presentation.module.feed.search

import android.text.TextUtils
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

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class SearchPresenterImpl(private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<SearchView>(), SearchPresenter {

    private lateinit var currentKeyword: String
    private var feeds: MutableList<FeedEntity> = mutableListOf()

    private var currentPage: Int = 0

    override fun search(content: String) {
        currentKeyword = content

        if (TextUtils.isEmpty(currentKeyword)) {
            view?.showSearchContentIsEmpty()
            return
        }
        currentPage = 0
        val paramProvider = FeedParamProvider()
        paramProvider.page(currentPage)
        paramProvider.searchContent(currentKeyword)

        feedRepositoryManager.executeSearchFeeds(paramProvider, RefreshFeedsObserver())
    }

    override fun searchViewTextChanged() {
        view?.showContent()
    }

    override fun searchFeedItemClick(feedEntity: FeedEntity) {
        Router.routeToFeedDetail(view?.ctx, feedEntity)
    }

    override fun refresh() {
        if (!TextUtils.isEmpty(currentKeyword)) {
            search(currentKeyword)
        }
    }

    override fun loadmore() {
        val paramProvider = FeedParamProvider()
        paramProvider.page(++currentPage)
        paramProvider.searchContent(currentKeyword)
        feedRepositoryManager.executeSearchFeeds(paramProvider, LoadmoreFeedsObserver())
    }

    override fun reload() {
        refresh()
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
            view?.showErrorView(msg)
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
            view!!.showErrorView(msg)
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