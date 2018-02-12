package me.chriskyle.ikan.data.repository.datastore.local

import io.reactivex.Observable
import me.chriskyle.ikan.data.repository.datastore.TokenDataStore
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.TokenParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class LocalTokenDataStore constructor(private val tokenCache: TokenCache)
    : TokenDataStore {

    override fun bindDeviceToken(tokenParamProvider: TokenParamProvider): Observable<Any> {
        return Observable.create<Any> {}
    }
}