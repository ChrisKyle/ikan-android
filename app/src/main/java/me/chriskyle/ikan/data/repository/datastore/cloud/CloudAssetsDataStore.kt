package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AssetsEntity
import me.chriskyle.ikan.data.entity.DenominationEntity
import me.chriskyle.ikan.data.repository.datastore.AssetsDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.AssetsService
import me.chriskyle.ikan.data.repository.datastore.params.provider.AssetsParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
class CloudAssetsDataStore(connector: Connector, tokenInterceptor: TokenInterceptor) :
        BaseCloudDataStore(tokenInterceptor), AssetsDataStore {

    private val assetsService: AssetsService = connector
            .getServiceCreator()
            .create(AssetsService::class.java)

    override fun getBalance(): Observable<AssetsEntity> {
        return assetsService.getBalance()
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    assetsService.getBalance()
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getDenominations(): Observable<List<DenominationEntity>> {
        return assetsService.getDenominations()
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    assetsService.getDenominations()
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }
}