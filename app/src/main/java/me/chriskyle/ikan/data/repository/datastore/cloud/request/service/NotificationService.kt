package me.chriskyle.ikan.data.repository.datastore.cloud.request.service

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.library.net.response.DataResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
interface NotificationService {

    @GET("/notification/")
    fun getNotifications(@QueryMap queryMap: Map<String, String>): Observable<DataResponse<PagerEntity<List<NotificationEntity>>>>

    @DELETE("/notification/{id}/")
    fun deleteNotification(@Path("id") id: String): Observable<DataResponse<NotificationEntity>>
}