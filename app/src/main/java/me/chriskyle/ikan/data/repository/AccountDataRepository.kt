package me.chriskyle.ikan.data.repository

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.factory.AccountDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider
import me.chriskyle.ikan.domain.repository.AccountRepository
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class AccountDataRepository @Inject constructor(private var accountDataStoreFactory: AccountDataStoreFactory) :
        AccountRepository {

    override fun login(accountParamProvider: AccountParamProvider): Observable<AccountEntity> =
            accountDataStoreFactory.createCloudDataStore().login(accountParamProvider)

    override fun getLogged(): AccountEntity? = accountDataStoreFactory.createLocalDataStore().getLogged()

    override fun getDetail(accountParamProvider: AccountParamProvider): Observable<AccountEntity> =
            accountDataStoreFactory.createCloudDataStore().getDetail(accountParamProvider)

    override fun update(accountParamProvider: AccountParamProvider): Observable<AccountEntity> =
            accountDataStoreFactory.createCloudDataStore().update(accountParamProvider)

    override fun bind(accountParamProvider: AccountParamProvider): Observable<AccountEntity> =
            accountDataStoreFactory.createCloudDataStore().bind(accountParamProvider)

    override fun unbind(accountParamProvider: AccountParamProvider): Observable<AccountEntity> =
            accountDataStoreFactory.createCloudDataStore().unbind(accountParamProvider)

    override fun logout() = accountDataStoreFactory.createLocalDataStore().logout()
}