package me.chriskyle.ikan.domain.repository.manager

import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.FuncDataRepository
import me.chriskyle.ikan.data.repository.datastore.params.provider.FuncParamProvider
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
class FuncRepositoryManager @Inject constructor(threadExecutor: ThreadExecutor,
                                                postExecutionThread: PostExecutionThread,
                                                private var funcDataRepository: FuncDataRepository) :
        BaseRepositoryManager(threadExecutor, postExecutionThread) {

    fun executeGetHotProblems(funcParamProvider: FuncParamProvider, subscriber: Observer<PagerEntity<List<ProblemEntity>>>) {
        funcDataRepository.getHotProblems(funcParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeSearchProblems(funcParamProvider: FuncParamProvider, subscriber: Observer<List<ProblemEntity>>) {
        funcDataRepository.searchProblem(funcParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeSendFeedback(funcParamProvider: FuncParamProvider, subscriber: Observer<Any>) {
        funcDataRepository.sendFeedback(funcParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetNewestVersion(subscriber: Observer<VersionEntity>){
        funcDataRepository.getNewestVersion()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }
}