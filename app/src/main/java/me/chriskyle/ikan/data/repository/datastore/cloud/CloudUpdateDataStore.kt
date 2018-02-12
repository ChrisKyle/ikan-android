package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.UpdateDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.UpdateService
import me.chriskyle.ikan.data.repository.datastore.params.provider.UpdateParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class CloudUpdateDataStore(connector: Connector, tokenInterceptor: TokenInterceptor) :
        BaseCloudDataStore(tokenInterceptor), UpdateDataStore {

    private val updateService: UpdateService = connector
            .getServiceCreator()
            .create(UpdateService::class.java)

    override fun checkUpdate(updateParamProvider: UpdateParamProvider): Observable<VersionEntity> {
        return updateService.checkUpdate(updateParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    updateService.checkUpdate(updateParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }
}
