package me.chriskyle.ikan.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import me.chriskyle.ikan.data.entity.SegmentEntity
import me.chriskyle.ikan.data.entity.WatchHistoryEntity
import me.chriskyle.ikan.data.repository.datastore.factory.FeedDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.FeedRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
@Singleton
class FeedDataRepository @Inject constructor(private var feedDataStoreFactory: FeedDataStoreFactory)
    : FeedRepository {

    override fun getHomeFeeds(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getHomeFeeds(feedParamProvider)

    override fun searchFeeds(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .searchFeeds(feedParamProvider)

    override fun getTrendingFeeds(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getTrendingFeeds(feedParamProvider)

    override fun getTrendingMoreFeeds(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getTrendingMoreFeeds(feedParamProvider)

    override fun getDiscoverFeeds(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getDiscoverFeeds(feedParamProvider)

    override fun getRecommendFeeds(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getRecommendFeeds(feedParamProvider)

    override fun getFeedDetail(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getFeedDetail(feedParamProvider)

    override fun editFeed(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .editFeed(feedParamProvider)

    override fun deleteFeed(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .deleteFeed(feedParamProvider)

    override fun likeFeed(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .likeFeed(feedParamProvider)

    override fun dislikeFeed(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .dislikeFeed(feedParamProvider)

    override fun buyFeed(feedParamProvider: FeedParamProvider): Observable<Any> = feedDataStoreFactory
            .createCloudDataStore()
            .buyFeed(feedParamProvider)

    override fun getFeedComments(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .getFeedComments(feedParamProvider)

    override fun addFeedComment(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createCloudDataStore()
            .addFeedComment(feedParamProvider)

    override fun getWatchHistories(feedParamProvider: FeedParamProvider) = feedDataStoreFactory
            .createLocalDataStore()
            .getWatchHistories(feedParamProvider)

    override fun addWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        feedDataStoreFactory
                .createLocalDataStore()
                .addWatchHistory(watchHistoryEntity)
    }

    override fun deleteWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        feedDataStoreFactory
                .createLocalDataStore()
                .deleteWatchHistory(watchHistoryEntity)
    }

    override fun clearWatchHistory(): Single<Long> {
        return feedDataStoreFactory
                .createLocalDataStore()
                .clearWatchHistory()
    }

    override fun getSegments(): Observable<List<SegmentEntity>> =
            Observable
                    .concat(feedDataStoreFactory.createLocalDataStore().getSegments()
                            .doAfterNext {
                                feedDataStoreFactory
                                        .createCloudDataStore()
                                        .getSegments()
                                        .subscribe(DefaultObserver())
                            },
                            feedDataStoreFactory.createCloudDataStore().getSegments())
}