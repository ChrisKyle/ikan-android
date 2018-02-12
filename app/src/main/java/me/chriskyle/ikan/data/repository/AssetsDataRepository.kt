package me.chriskyle.ikan.data.repository

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AssetsEntity
import me.chriskyle.ikan.data.entity.DenominationEntity
import me.chriskyle.ikan.data.repository.datastore.factory.AssetsDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.AssetsParamProvider
import me.chriskyle.ikan.domain.repository.AssetsRepository
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
class AssetsDataRepository @Inject constructor(private var assetsDataStoreFactory: AssetsDataStoreFactory) :
        AssetsRepository {

    override fun getBalance(): Observable<AssetsEntity> {
        return assetsDataStoreFactory.createCloudDataStore().getBalance()
    }

    override fun getDenominations(): Observable<List<DenominationEntity>> {
        return assetsDataStoreFactory.createCloudDataStore().getDenominations()
    }
}