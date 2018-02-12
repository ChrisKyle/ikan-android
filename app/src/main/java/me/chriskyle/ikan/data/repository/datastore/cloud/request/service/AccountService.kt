package me.chriskyle.ikan.data.repository.datastore.cloud.request.service

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.entity.LoginEntity
import me.chriskyle.library.net.response.DataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
interface AccountService {

    @POST("/account/")
    fun login(@Body paramMap: Map<String, String>): Observable<DataResponse<LoginEntity>>

    @GET("/account/{id}/")
    fun getDetail(@Path("id") id: String): Observable<DataResponse<AccountEntity>>

    @POST("/account/bind/")
    fun bind(@Body paramMap: Map<String, String>): Observable<DataResponse<AccountEntity>>

    @POST("/account/unbind/")
    fun unbind(@Body paramMap: Map<String, String>): Observable<DataResponse<AccountEntity>>

    @PATCH("/account/{id}/")
    fun update(@Path("id") id: String, @Body paramMap: Map<String, String>): Observable<DataResponse<AccountEntity>>

    @Multipart
    @POST("/upload/")
    fun updateAvatar(@PartMap map: Map<String, RequestBody>, @Part file: MultipartBody.Part): Observable<DataResponse<Unit>>
}