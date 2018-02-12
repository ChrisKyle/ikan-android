package me.chriskyle.ikan.data.repository.datastore.cloud.request.service

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AssetsEntity
import me.chriskyle.ikan.data.entity.DenominationEntity
import me.chriskyle.library.net.response.DataResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
interface AssetsService {

    @GET("/account/balance/")
    fun getBalance(): Observable<DataResponse<AssetsEntity>>

    @GET("/denomination/")
    fun getDenominations(): Observable<DataResponse<List<DenominationEntity>>>
}