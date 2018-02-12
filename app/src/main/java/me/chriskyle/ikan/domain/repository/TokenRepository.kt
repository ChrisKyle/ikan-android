package me.chriskyle.ikan.domain.repository

import io.reactivex.Observable
import me.chriskyle.ikan.data.repository.datastore.params.provider.TokenParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/18.
 */
interface TokenRepository {

    fun bindDeviceToken(tokenParamProvider: TokenParamProvider): Observable<Any>
}