package me.chriskyle.ikan.data.repository.datastore.local.cache.impl

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.*
import me.chriskyle.ikan.data.repository.datastore.local.cache.FeedCache
import me.chriskyle.ikan.data.repository.datastore.local.db.DbHelper
import me.chriskyle.library.rxcupboard.RxCupboard

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/25.
 */
class FeedCacheImpl(val context: Context) : FeedCache {

    private var connection = DbHelper.getConnection(context)

    override fun getHomeFeeds(): Observable<PagerEntity<List<FeedEntity>>> {
        throw UnsupportedOperationException()
    }

    override fun getTrendingFeeds(): Observable<PagerEntity<List<TrendingFeedEntity>>> {
        throw UnsupportedOperationException()
    }

    override fun getDiscoverFeeds(): Observable<List<FeedEntity>> {
        throw UnsupportedOperationException()
    }

    override fun getRecommendFeeds(): Observable<List<RecommendFeedEntity>> {
        throw UnsupportedOperationException()
    }

    override fun getFeedSegments(): Observable<List<SegmentEntity>> {
        return RxCupboard.withDefault(connection)
                .query(SegmentEntity::class.java)
                .toList()
                .toObservable()
    }

    override fun addFeedSegments(segments: List<SegmentEntity>): Disposable =
            Single.concat(RxCupboard.withDefault(connection).deleteAll(SegmentEntity::class.java),
                    RxCupboard.withDefault(connection).put(segments)).subscribe({})

    override fun getWatchHistories(): Observable<List<WatchHistoryEntity>> {
        return RxCupboard.withDefault(connection)
                .query(WatchHistoryEntity::class.java)
                .toList()
                .toObservable()
    }

    override fun addWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        RxCupboard.withDefault(connection).putDirectly(watchHistoryEntity)
    }

    override fun deleteWatchHistory(watchHistoryEntity: WatchHistoryEntity) {
        RxCupboard.withDefault(connection).delete(watchHistoryEntity)
    }

    override fun clearWatchHistory(): Single<Long> {
        return RxCupboard.withDefault(connection).deleteAll(WatchHistoryEntity::class.java)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: FeedCacheImpl? = null

        fun getInstance(context: Context): FeedCacheImpl = INSTANCE
                ?: FeedCacheImpl(context.applicationContext).also { INSTANCE = it }
    }
}