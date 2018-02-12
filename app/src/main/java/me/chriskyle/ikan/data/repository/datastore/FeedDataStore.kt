package me.chriskyle.ikan.data.repository.datastore

import io.reactivex.Observable
import io.reactivex.Single
import me.chriskyle.ikan.data.entity.*
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
interface FeedDataStore {

    fun getHomeFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>>

    fun getTrendingFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<TrendingFeedEntity>>>

    fun getTrendingMoreFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>>

    fun getDiscoverFeeds(feedParamProvider: FeedParamProvider): Observable<List<FeedEntity>>

    fun getRecommendFeeds(feedParamProvider: FeedParamProvider): Observable<List<RecommendFeedEntity>>

    fun searchFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>>

    fun getFeedDetail(feedParamProvider: FeedParamProvider): Observable<FeedEntity>

    fun editFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity>

    fun deleteFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity>

    fun getFeedComments(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<CommentEntity>>>

    fun addFeedComment(feedParamProvider: FeedParamProvider): Observable<CommentEntity>

    fun likeFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity>

    fun dislikeFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity>

    fun buyFeed(feedParamProvider: FeedParamProvider): Observable<Any>

    fun getWatchHistories(feedParamProvider: FeedParamProvider): Observable<List<WatchHistoryEntity>>

    fun addWatchHistory(watchHistoryEntity: WatchHistoryEntity)

    fun deleteWatchHistory(watchHistoryEntity: WatchHistoryEntity)

    fun clearWatchHistory(): Single<Long>

    fun getSegments(): Observable<List<SegmentEntity>>
}
