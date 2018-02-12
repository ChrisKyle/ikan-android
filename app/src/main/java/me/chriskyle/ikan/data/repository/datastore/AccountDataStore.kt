package me.chriskyle.ikan.data.repository.datastore

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
interface AccountDataStore {

    fun login(accountParamProvider: AccountParamProvider): Observable<AccountEntity>

    fun getLogged(): AccountEntity?

    fun getDetail(accountParamProvider: AccountParamProvider): Observable<AccountEntity>

    fun update(accountParamProvider: AccountParamProvider): Observable<AccountEntity>

    fun bind(accountParamProvider: AccountParamProvider): Observable<AccountEntity>

    fun unbind(accountParamProvider: AccountParamProvider): Observable<AccountEntity>

    fun logout()
}