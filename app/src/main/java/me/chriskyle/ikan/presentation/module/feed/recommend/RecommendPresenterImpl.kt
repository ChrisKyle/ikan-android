package me.chriskyle.ikan.presentation.module.feed.recommend

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.data.entity.RecommendFeedEntity
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
class RecommendPresenterImpl(private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<RecommendView>(), RecommendPresenter {

    override fun loadFeeds() {
        view?.showLoadingView()

        feedRepositoryManager.executeRecommendFeeds(FeedParamProvider(), LoadFeedsObserver())
    }

    override fun refresh() {
        feedRepositoryManager.executeRecommendFeeds(FeedParamProvider(), RefreshFeedsObserver())
    }

    override fun loadmore() {
    }

    override fun reload() {
        loadFeeds()
    }

    override fun feedItemClick(position: Int, feed: FeedEntity) {
        Router.routeToFeedDetail(view?.ctx, feed)
    }

    private fun loadFeedsSuccess(recommendFeeds: List<RecommendFeedEntity>) {
        if (recommendFeeds.isEmpty() || recommendFeeds[0].feeds == null || recommendFeeds[0].feeds!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showContent()
            view?.renderFeeds(recommendFeeds[0].feeds!!)
        }
    }

    private fun loadFeedsError(e: Throwable) {
        view?.let {
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private fun refreshFeedsSuccess(recommendFeeds: List<RecommendFeedEntity>) {
        view?.showRefreshCompleted()

        if (recommendFeeds.isEmpty() || recommendFeeds[0].feeds == null || recommendFeeds[0].feeds!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showContent()
            view?.renderTitle(recommendFeeds[0].name!!)
            view?.renderFeeds(recommendFeeds[0].feeds!!)
        }
    }

    private fun refreshFeedsError(e: Throwable) {
        view?.let {
            view!!.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private inner class LoadFeedsObserver : DefaultObserver<List<RecommendFeedEntity>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: List<RecommendFeedEntity>) {
            loadFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadFeedsError(e)
        }
    }

    private inner class RefreshFeedsObserver : DefaultObserver<List<RecommendFeedEntity>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: List<RecommendFeedEntity>) {
            refreshFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            refreshFeedsError(e)
        }
    }
}