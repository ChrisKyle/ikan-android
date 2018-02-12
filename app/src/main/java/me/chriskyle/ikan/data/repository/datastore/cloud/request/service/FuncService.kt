package me.chriskyle.ikan.data.repository.datastore.cloud.request.service

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.library.net.response.DataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
interface FuncService {

    @GET("/solution/")
    fun getHotProblems(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<PagerEntity<List<ProblemEntity>>>>

    @GET("/solution/search/")
    fun searchProblems(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<List<ProblemEntity>>>

    @POST("/feedback/")
    fun sendFeedback(@Body paramMap: Map<String, String>): Observable<DataResponse<Any>>

    @GET("/version/")
    fun getNewestVersion() : Observable<DataResponse<VersionEntity>>
}