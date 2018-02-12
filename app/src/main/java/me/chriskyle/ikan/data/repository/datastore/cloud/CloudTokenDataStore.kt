package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import me.chriskyle.ikan.data.repository.datastore.TokenDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.TokenService
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.TokenParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class CloudTokenDataStore(apiConnector: Connector, private val tokenCache: TokenCache)
    : TokenDataStore {

    private val tokenService: TokenService = apiConnector
            .getServiceCreator()
            .create(TokenService::class.java)

    override fun bindDeviceToken(tokenParamProvider: TokenParamProvider): Observable<Any> {
        return tokenService.bindDeviceToken(tokenParamProvider.optionalParam.map)
                .flatMap({ dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                })
    }
}
