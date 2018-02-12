package me.chriskyle.ikan.presentation.module.feed.watchhistory

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.WatchHistoryEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class WatchHistoryPresenterImpl(private val feedRepositoryManager: FeedRepositoryManager)
    : BasePresenter<WatchHistoryView>(), WatchHistoryPresenter {

    override fun loadWatchHistories() {
        view?.showContent()

        feedRepositoryManager.executeGetWatchHistories(FeedParamProvider(), LoadWatchHistoriesObserver())
    }

    override fun refresh() {
    }

    override fun loadmore() {
    }

    override fun reload() {
    }

    fun loadWatchHistoriesSuccess(list: List<WatchHistoryEntity>) {
        if (list.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showContent()
            view?.renderFeeds(list)
        }
    }

    fun loadWatchHistoriesError(e: Throwable) {
        view!!.showErrorView(null)
        view!!.showStatusMsg(view?.ctx?.getString(R.string.lce_error_des)!!)
    }

    inner class LoadWatchHistoriesObserver : DefaultObserver<List<WatchHistoryEntity>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: List<WatchHistoryEntity>) {
            loadWatchHistoriesSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadWatchHistoriesError(e)
        }
    }
}