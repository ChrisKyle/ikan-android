package me.chriskyle.ikan.data.repository

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.factory.FuncDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.FuncParamProvider
import me.chriskyle.ikan.domain.repository.FuncRepository
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
class FuncDataRepository @Inject constructor(private var funcDataStoreFactory: FuncDataStoreFactory) :
        FuncRepository {

    override fun getHotProblems(funcParamProvider: FuncParamProvider) = funcDataStoreFactory
            .createCloudDataStore()
            .getHotProblems(funcParamProvider)

    override fun searchProblem(funcParamProvider: FuncParamProvider) = funcDataStoreFactory
            .createCloudDataStore()
            .searchProblem(funcParamProvider)

    override fun sendFeedback(funcParamProvider: FuncParamProvider) = funcDataStoreFactory
            .createCloudDataStore()
            .sendFeedback(funcParamProvider)

    override fun getNewestVersion(): Observable<VersionEntity> = funcDataStoreFactory
            .createCloudDataStore()
            .getNewestVersion()

}
