package me.chriskyle.ikan.data.repository.datastore.cloud.request.service

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.library.net.response.DataResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
interface UpdateService {

    @GET("/update")
    fun checkUpdate(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<VersionEntity>>
}