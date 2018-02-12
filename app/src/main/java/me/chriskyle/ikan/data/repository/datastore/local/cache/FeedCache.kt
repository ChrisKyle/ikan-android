package me.chriskyle.ikan.data.repository.datastore.local.cache

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/25.
 */
interface FeedCache {

    fun getHomeFeeds(): Observable<PagerEntity<List<FeedEntity>>>

    fun getTrendingFeeds(): Observable<PagerEntity<List<TrendingFeedEntity>>>

    fun getDiscoverFeeds(): Observable<List<FeedEntity>>

    fun getRecommendFeeds(): Observable<List<RecommendFeedEntity>>

    fun getFeedSegments(): Observable<List<SegmentEntity>>

    fun addFeedSegments(segments: List<SegmentEntity>): Disposable

    fun getWatchHistories(): Observable<List<WatchHistoryEntity>>

    fun addWatchHistory(watchHistoryEntity: WatchHistoryEntity)

    fun deleteWatchHistory(watchHistoryEntity: WatchHistoryEntity)

    fun clearWatchHistory(): Single<Long>
}