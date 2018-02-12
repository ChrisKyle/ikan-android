package me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor

import io.reactivex.Observable
import io.reactivex.functions.Function
import me.chriskyle.ikan.data.entity.TokenEntity
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.TokenService
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.TokenParamProvider
import me.chriskyle.ikan.presentation.event.ReLoginEvent
import me.chriskyle.library.eventbus.Bus
import me.chriskyle.library.net.connection.Connector
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
@Singleton
class TokenInterceptor @Inject internal constructor(private val bus: Bus, connector: Connector,
                                                    private val tokenCache: TokenCache) {

    private val tokenService: TokenService = connector
            .getServiceCreator()
            .create(TokenService::class.java)

    fun <T> refreshTokenAndRetry(toBeResumedObservable: Observable<T>): Function<Throwable, out Observable<out T>> {
        return Function { topThrowable ->
            if (isHttp401Error(topThrowable))
                refreshToken()
                        .doOnError { throwable ->
                            if (isHttp403Error(throwable)) {
                                bus.post(ReLoginEvent())
                            }
                        }
                        .doOnNext { tokenEntity ->
                            tokenCache.put(tokenEntity)
                        }
                        .flatMap {
                            toBeResumedObservable
                        }
            else
                Observable.error(topThrowable)
        }
    }

    private fun refreshToken(): Observable<TokenEntity> {
        val tokenParamProvider = TokenParamProvider()
        tokenParamProvider.refreshToken(tokenCache.get()?.reToken!!)

        return tokenService.refreshToken(tokenParamProvider.optionalParam.map)
                .flatMap({ tokenEntityDataResponse ->
                    ResponseFlatResult.flatResult(tokenEntityDataResponse)
                })
    }

    private fun isHttp401Error(throwable: Throwable): Boolean {
        return if (throwable is HttpException) {
            throwable.code() == 401
        } else
            false
    }

    private fun isHttp403Error(throwable: Throwable): Boolean {
        return if (throwable is HttpException) {
            throwable.code() == 403
        } else
            false
    }
}
