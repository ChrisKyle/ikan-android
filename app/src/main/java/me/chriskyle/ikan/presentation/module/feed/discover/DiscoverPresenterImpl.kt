package me.chriskyle.ikan.presentation.module.feed.discover

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.FeedEntity
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
class DiscoverPresenterImpl(private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<DiscoverView>(), DiscoverPresenter {

    override fun loadFeeds() {
        view?.showLoadingView()

        feedRepositoryManager.executeDiscoverFeeds(FeedParamProvider(), LoadDiscoverFeedsObserver())
    }

    override fun reloadFeeds() {
        loadFeeds()
    }

    override fun refreshFeeds() {
        loadFeeds()
    }

    override fun feedDetail(feed: FeedEntity) {
        Router.routeToFeedDetail(view?.ctx, feed)
    }

    private fun loadDiscoverFeedsSuccess(feeds: List<FeedEntity>) {
        if (feeds.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showContent()
            view?.renderFeeds(feeds)
        }
    }

    private fun loadDiscoverFeedsError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showErrorView(null)
        view?.showStatusMsg(msg)
    }

    private inner class LoadDiscoverFeedsObserver : DefaultObserver<List<FeedEntity>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: List<FeedEntity>) {
            loadDiscoverFeedsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadDiscoverFeedsError(e)
        }
    }
}