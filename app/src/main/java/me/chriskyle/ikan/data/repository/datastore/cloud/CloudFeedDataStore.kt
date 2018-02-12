package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import io.reactivex.Single
import me.chriskyle.ikan.data.entity.*
import me.chriskyle.ikan.data.repository.datastore.FeedDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.FeedService
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.local.cache.FeedCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class CloudFeedDataStore(connector: Connector, tokenInterceptor: TokenInterceptor,
                         private val feedCache: FeedCache)
    : BaseCloudDataStore(tokenInterceptor), FeedDataStore {

    private val feedService: FeedService = connector
            .getServiceCreator()
            .create(FeedService::class.java)

    override fun getFeedDetail(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        return feedService.getFeedDetail(feedParamProvider.getFeedId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getFeedDetail(feedParamProvider.getFeedId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun editFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        return feedService.editFeed(feedParamProvider.getFeedId(), feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.editFeed(feedParamProvider.getFeedId(), feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun deleteFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        return feedService.deleteFeed(feedParamProvider.getFeedId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.deleteFeed(feedParamProvider.getFeedId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getFeedComments(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<CommentEntity>>> {
        return feedService.getFeedComments(feedParamProvider.getFeedId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getFeedComments(feedParamProvider.getFeedId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun addFeedComment(feedParamProvider: FeedParamProvider): Observable<CommentEntity> {
        return feedService.addFeedComment(feedParamProvider.getFeedId(), feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.addFeedComment(feedParamProvider.getFeedId(), feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun likeFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        return feedService.likeFeed(feedParamProvider.getFeedId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.likeFeed(feedParamProvider.getFeedId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun dislikeFeed(feedParamProvider: FeedParamProvider): Observable<FeedEntity> {
        return feedService.dislikeFeed(feedParamProvider.getFeedId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.dislikeFeed(feedParamProvider.getFeedId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun buyFeed(feedParamProvider: FeedParamProvider): Observable<Any> {
        return feedService.buyFeed(feedParamProvider.getFeedId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.buyFeed(feedParamProvider.getFeedId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun searchFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>> {
        return feedService.searchFeeds(feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getHomeFeeds(feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getHomeFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>> {
        return feedService.getHomeFeeds(feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getHomeFeeds(feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getTrendingFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<TrendingFeedEntity>>> {
        return feedService.getTrendingFeeds(feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getTrendingFeeds(feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getTrendingMoreFeeds(feedParamProvider: FeedParamProvider): Observable<PagerEntity<List<FeedEntity>>> {
        return feedService.getTrendingMoreFeeds(feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getTrendingMoreFeeds(feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getDiscoverFeeds(feedParamProvider: FeedParamProvider): Observable<List<FeedEntity>> {
        return feedService.getDiscoverFeeds(feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getDiscoverFeeds(feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getRecommendFeeds(feedParamProvider: FeedParamProvider): Observable<List<RecommendFeedEntity>> {
        return feedService.getRecommendFeeds(feedParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getRecommendFeeds(feedParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getWatchHistories(feedParamProvider: FeedParamProvider): Observable<List<WatchHistoryEntity>> {
        throw UnsupportedOperationException()
    }

    override fun addWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        throw UnsupportedOperationException()
    }

    override fun deleteWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        throw UnsupportedOperationException()
    }

    override fun clearWatchHistory(): Single<Long> {
        throw UnsupportedOperationException()
    }

    override fun getSegments(): Observable<List<SegmentEntity>> {
        return feedService.getSegments()
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    feedService.getSegments()
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
                .doOnNext { it: List<SegmentEntity> ->
                    feedCache.addFeedSegments(it)
                }
    }
}
