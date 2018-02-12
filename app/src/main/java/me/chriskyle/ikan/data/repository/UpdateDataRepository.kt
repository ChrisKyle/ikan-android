package me.chriskyle.ikan.data.repository

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.factory.UpdateDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.UpdateParamProvider
import me.chriskyle.ikan.domain.repository.UpdateRepository
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class UpdateDataRepository @Inject constructor(private var updateDataStoreFactory: UpdateDataStoreFactory) :
        UpdateRepository {

    override fun checkUpdate(updateParamProvider: UpdateParamProvider): Observable<VersionEntity> {
        return updateDataStoreFactory.createCloudDataStore().checkUpdate(updateParamProvider)
    }
}
