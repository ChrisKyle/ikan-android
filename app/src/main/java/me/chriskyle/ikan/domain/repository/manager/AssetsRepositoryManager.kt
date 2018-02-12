package me.chriskyle.ikan.domain.repository.manager

import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.AssetsEntity
import me.chriskyle.ikan.data.entity.DenominationEntity
import me.chriskyle.ikan.data.repository.AssetsDataRepository
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import me.chriskyle.ikan.domain.DefaultObserver
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
@Singleton
class AssetsRepositoryManager @Inject constructor(threadExecutor: ThreadExecutor,
                                                  postExecutionThread: PostExecutionThread,
                                                  private var assetsDataRepository: AssetsDataRepository) :
        BaseRepositoryManager(threadExecutor, postExecutionThread) {

    fun executeGetBalance(subscriber: DefaultObserver<AssetsEntity>) {
        assetsDataRepository.getBalance()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetDenominations(subscriber: DefaultObserver<List<DenominationEntity>>) {
        assetsDataRepository.getDenominations()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }
}