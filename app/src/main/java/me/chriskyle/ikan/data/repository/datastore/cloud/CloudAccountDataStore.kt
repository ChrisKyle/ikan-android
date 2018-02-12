package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.entity.TokenEntity
import me.chriskyle.ikan.data.repository.datastore.AccountDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.AccountService
import me.chriskyle.ikan.data.repository.datastore.local.cache.AccountCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class CloudAccountDataStore(connector: Connector, tokenInterceptor: TokenInterceptor,
                            private val accountCache: AccountCache,
                            private val tokenCache: TokenCache) :
        BaseCloudDataStore(tokenInterceptor), AccountDataStore {

    private val accountService: AccountService = connector
            .getServiceCreator()
            .create(AccountService::class.java)

    override fun login(accountParamProvider: AccountParamProvider): Observable<AccountEntity> {
        return accountService.login(accountParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    accountService.login(accountParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
                .map { loginEntity ->
                    tokenCache.put(TokenEntity(loginEntity.token, loginEntity.refreshToken))
                    loginEntity.account
                }
                .doOnNext { accountEntity: AccountEntity ->
                    accountCache.put(accountEntity)
                }

    }

    override fun getLogged(): AccountEntity? = null

    override fun getDetail(accountParamProvider: AccountParamProvider): Observable<AccountEntity> {
        return accountService.getDetail(accountParamProvider.getAccountId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    accountService.getDetail(accountParamProvider.getAccountId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
                .doOnNext { accountEntity: AccountEntity ->
                    accountCache.put(accountEntity)
                }
    }

    override fun update(accountParamProvider: AccountParamProvider): Observable<AccountEntity> {
        return accountService.update(accountParamProvider.getAccountId(), accountParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    accountService.update(accountParamProvider.getAccountId(), accountParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
                .doOnNext { accountEntity: AccountEntity ->
                    accountCache.put(accountEntity)
                }
    }

    override fun bind(accountParamProvider: AccountParamProvider): Observable<AccountEntity> {
        return accountService.bind(accountParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    accountService.bind(accountParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun unbind(accountParamProvider: AccountParamProvider): Observable<AccountEntity> {
        return accountService.unbind(accountParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    accountService.unbind(accountParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }
                ))
    }

    override fun logout() {
    }
}