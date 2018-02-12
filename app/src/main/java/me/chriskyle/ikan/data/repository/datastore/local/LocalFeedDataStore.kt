package me.chriskyle.ikan.data.repository.datastore.local

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.*
import me.chriskyle.ikan.data.repository.datastore.FeedDataStore
import me.chriskyle.ikan.data.repository.datastore.local.cache.FeedCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class LocalFeedDataStore(private val feedCache: FeedCache) : FeedDataStore {

    override fun getHomeFeeds(feedParamProvider: FeedParamProvider) = feedCache.getHomeFeeds()

    override fun searchFeeds(feedParamProvider: FeedParamProvider) = throw UnsupportedOperationException()

    override fun getTrendingFeeds(feedParamProvider: FeedParamProvider) = feedCache.getTrendingFeeds()

    override fun getTrendingMoreFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>> {
        throw UnsupportedOperationException()
    }

    override fun getDiscoverFeeds(feedParamProvider: FeedParamProvider) = feedCache.getDiscoverFeeds()

    override fun getRecommendFeeds(feedParamProvider: FeedParamProvider) = feedCache.getRecommendFeeds()

    override fun getFeedDetail(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        throw UnsupportedOperationException()
    }

    override fun editFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        throw UnsupportedOperationException()
    }

    override fun deleteFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        throw UnsupportedOperationException()
    }

    override fun getFeedComments(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<CommentEntity>>> {
        throw UnsupportedOperationException()
    }

    override fun addFeedComment(feedParamProvider: FeedParamProvider): Observable<CommentEntity> {
        throw UnsupportedOperationException()
    }

    override fun likeFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        throw UnsupportedOperationException()
    }

    override fun dislikeFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        throw UnsupportedOperationException()
    }

    override fun buyFeed(feedParamProvider: FeedParamProvider): Observable<Any> {
        throw UnsupportedOperationException()
    }

    override fun getWatchHistories(feedParamProvider: FeedParamProvider) = feedCache.getWatchHistories()

    override fun addWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        feedCache.addWatchHistory(watchHistoryEntity)
    }

    override fun deleteWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        feedCache.deleteWatchHistory(watchHistoryEntity)
    }

    override fun clearWatchHistory() = feedCache.clearWatchHistory()

    override fun getSegments(): Observable<List<SegmentEntity>> {
        return Observable.create { emitter ->
            feedCache.getFeedSegments()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe { list ->
                        if (list.isNotEmpty()) {
                            emitter.onNext(list)
                        }
                        emitter.onComplete()
                    }
        }
    }
}
