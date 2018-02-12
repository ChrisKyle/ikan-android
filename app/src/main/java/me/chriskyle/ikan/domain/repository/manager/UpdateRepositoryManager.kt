package me.chriskyle.ikan.domain.repository.manager

import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.UpdateDataRepository
import me.chriskyle.ikan.data.repository.datastore.params.provider.UpdateParamProvider
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class UpdateRepositoryManager @Inject constructor(threadExecutor: ThreadExecutor,
                                                  postExecutionThread: PostExecutionThread,
                                                  private var updateDataRepository: UpdateDataRepository) :
        BaseRepositoryManager(threadExecutor, postExecutionThread) {

    fun executeCheckUpdate(updateParamProvider: UpdateParamProvider, subscriber: Observer<VersionEntity>) {
        updateDataRepository.checkUpdate(updateParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }
}