package me.chriskyle.ikan.data.repository.datastore.cloud.request.service

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.*
import me.chriskyle.library.net.response.DataResponse
import retrofit2.http.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
interface FeedService {

    @GET("/feed/home/")
    fun getHomeFeeds(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<PagerEntity<List<FeedEntity>>>>

    @GET("/feed/trending/")
    fun getTrendingFeeds(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<PagerEntity<List<TrendingFeedEntity>>>>

    @GET("/feed/trending/more/")
    fun getTrendingMoreFeeds(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<PagerEntity<List<FeedEntity>>>>

    @GET("/feed/discover/")
    fun getDiscoverFeeds(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<List<FeedEntity>>>

    @GET("/feed/recommend/")
    fun getRecommendFeeds(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<List<RecommendFeedEntity>>>

    @GET("/feed/search/")
    fun searchFeeds(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<PagerEntity<List<FeedEntity>>>>

    @GET("/feed/{id}/")
    fun getFeedDetail(@Path("id") id: String): Observable<DataResponse<FeedEntity>>

    @POST("/feed/")
    fun addFeed(@Body map: Map<String, String>): Observable<DataResponse<FeedEntity>>

    @PATCH("/feed/{id}/")
    fun editFeed(@Path("id") id: String, @Body paramMap: Map<String, String>): Observable<DataResponse<FeedEntity>>

    @DELETE("/feed/{id}/")
    fun deleteFeed(@Path("id") id: String): Observable<DataResponse<FeedEntity>>

    @GET("/feed/{id}/comment/")
    fun getFeedComments(@Path("id") id: String): Observable<DataResponse<PagerEntity<List<CommentEntity>>>>

    @POST("/feed/{id}/comment/")
    fun addFeedComment(@Path("id") id: String, @Body map: Map<String, String>): Observable<DataResponse<CommentEntity>>

    @POST("/feed/{id}/like/")
    fun likeFeed(@Path("id") id: String): Observable<DataResponse<FeedEntity>>

    @DELETE("/feed/{id}/like/")
    fun dislikeFeed(@Path("id") id: String): Observable<DataResponse<FeedEntity>>

    @POST("/feed/{id}/buy/")
    fun buyFeed(@Path("id") id: String): Observable<DataResponse<Any>>

    @GET("/segment/")
    fun getSegments(): Observable<DataResponse<List<SegmentEntity>>>
}
