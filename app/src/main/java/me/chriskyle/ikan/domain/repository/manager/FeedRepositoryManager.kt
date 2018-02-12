package me.chriskyle.ikan.domain.repository.manager

import io.reactivex.MaybeObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.*
import me.chriskyle.ikan.data.repository.FeedDataRepository
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/17.
 */
@Singleton
class FeedRepositoryManager @Inject constructor(threadExecutor: ThreadExecutor,
                                                postExecutionThread: PostExecutionThread,
                                                private var feedDataRepository: FeedDataRepository) :
        BaseRepositoryManager(threadExecutor, postExecutionThread) {

    fun executeGetFeedDetail(feedParamProvider: FeedParamProvider, subscriber: Observer<FeedEntity>) {
        feedDataRepository.getFeedDetail(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetHomeFeeds(feedParamProvider: FeedParamProvider, subscriber: Observer<PagerEntity<List<FeedEntity>>>) {
        feedDataRepository.getHomeFeeds(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeSearchFeeds(feedParamProvider: FeedParamProvider, subscriber: Observer<PagerEntity<List<FeedEntity>>>) {
        feedDataRepository.searchFeeds(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetTrendingFeeds(feedParamProvider: FeedParamProvider, subscriber: Observer<PagerEntity<List<TrendingFeedEntity>>>) {
        feedDataRepository.getTrendingFeeds(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetTrendingMoreFeeds(feedParamProvider: FeedParamProvider, subscriber: Observer<PagerEntity<List<FeedEntity>>>) {
        feedDataRepository.getTrendingMoreFeeds(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeDiscoverFeeds(feedParamProvider: FeedParamProvider, subscriber: Observer<List<FeedEntity>>) {
        feedDataRepository.getDiscoverFeeds(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeRecommendFeeds(feedParamProvider: FeedParamProvider, subscriber: Observer<List<RecommendFeedEntity>>) {
        feedDataRepository.getRecommendFeeds(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeAddFeedComment(feedParamProvider: FeedParamProvider, subscriber: Observer<CommentEntity>) {
        feedDataRepository.addFeedComment(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetFeedComments(feedParamProvider: FeedParamProvider, subscriber: Observer<PagerEntity<List<CommentEntity>>>) {
        feedDataRepository.getFeedComments(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeLikeFeed(feedParamProvider: FeedParamProvider, subscriber: Observer<FeedEntity>) {
        feedDataRepository.likeFeed(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeDislikeFeed(feedParamProvider: FeedParamProvider, subscriber: Observer<FeedEntity>) {
        feedDataRepository.dislikeFeed(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeBuyFeed(feedParamProvider: FeedParamProvider, subscriber: Observer<Any>) {
        feedDataRepository.buyFeed(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetWatchHistories(feedParamProvider: FeedParamProvider, subscriber: Observer<List<WatchHistoryEntity>>) {
        feedDataRepository.getWatchHistories(feedParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeAddWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        feedDataRepository.addWatchHistory(watchHistoryEntity)
    }

    fun executeDeleteWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        feedDataRepository.deleteWatchHistory(watchHistoryEntity)
    }

    fun executeClearWatchHistory(subscriber: SingleObserver<Long>) {
        feedDataRepository.clearWatchHistory()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetSegments(subscriber: MaybeObserver<List<SegmentEntity>>) {
        feedDataRepository.getSegments()
                .firstElement()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }
}