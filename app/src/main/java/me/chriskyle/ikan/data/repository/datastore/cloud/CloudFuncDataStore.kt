package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.FuncDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.FuncService
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.params.provider.FuncParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
class CloudFuncDataStore(connector: Connector, tokenInterceptor: TokenInterceptor)
    : BaseCloudDataStore(tokenInterceptor), FuncDataStore {

    private val funcService: FuncService = connector
            .getServiceCreator()
            .create(FuncService::class.java)

    override fun getHotProblems(funcParamProvider: FuncParamProvider): Observable<PagerEntity<List<ProblemEntity>>> {
        return funcService.getHotProblems(funcParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    funcService.getHotProblems(funcParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun searchProblem(funcParamProvider: FuncParamProvider): Observable<List<ProblemEntity>> {
        return funcService.searchProblems(funcParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    funcService.searchProblems(funcParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun sendFeedback(funcParamProvider: FuncParamProvider): Observable<Any> {
        return funcService.sendFeedback(funcParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    funcService.sendFeedback(funcParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun getNewestVersion(): Observable<VersionEntity> {
        return funcService.getNewestVersion()
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    funcService.getNewestVersion()
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }
}