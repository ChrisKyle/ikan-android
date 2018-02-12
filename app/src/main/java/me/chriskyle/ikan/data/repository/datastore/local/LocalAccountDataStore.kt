package me.chriskyle.ikan.data.repository.datastore.local

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.AccountDataStore
import me.chriskyle.ikan.data.repository.datastore.local.cache.AccountCache
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/29.
 */
class LocalAccountDataStore(private val accountCache: AccountCache,
                            private val tokenCache: TokenCache) : AccountDataStore {

    override fun login(accountParamProvider: AccountParamProvider): Observable<AccountEntity> = throw  UnsupportedOperationException()

    override fun getLogged(): AccountEntity? = accountCache.get()

    override fun getDetail(accountParamProvider: AccountParamProvider): Observable<AccountEntity> = throw  UnsupportedOperationException()

    override fun update(accountParamProvider: AccountParamProvider): Observable<AccountEntity> = throw  UnsupportedOperationException()

    override fun bind(accountParamProvider: AccountParamProvider): Observable<AccountEntity> = throw  UnsupportedOperationException()

    override fun unbind(accountParamProvider: AccountParamProvider): Observable<AccountEntity> = throw  UnsupportedOperationException()

    override fun logout() {
        accountCache.evict()
        tokenCache.evict()
    }
}