package me.chriskyle.ikan.data.repository.datastore

import io.reactivex.Observable
import me.chriskyle.ikan.data.repository.datastore.params.provider.TokenParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
interface TokenDataStore {

    fun bindDeviceToken(tokenParamProvider: TokenParamProvider): Observable<Any>
}
