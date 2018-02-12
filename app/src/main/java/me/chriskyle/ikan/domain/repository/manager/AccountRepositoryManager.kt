package me.chriskyle.ikan.domain.repository.manager

import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.AccountDataRepository
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
class AccountRepositoryManager @Inject constructor(threadExecutor: ThreadExecutor,
                                                   postExecutionThread: PostExecutionThread,
                                                   private var accountDataRepository: AccountDataRepository) :
        BaseRepositoryManager(threadExecutor, postExecutionThread) {

    fun executeLogin(accountParamProvider: AccountParamProvider, subscriber: Observer<AccountEntity>) {
        accountDataRepository.login(accountParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeGetLogged(): AccountEntity? = accountDataRepository.getLogged()

    fun executeGetDetail(accountParamProvider: AccountParamProvider, subscriber: Observer<AccountEntity>) {
        accountDataRepository.getDetail(accountParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeUpdate(accountParamProvider: AccountParamProvider, subscriber: Observer<AccountEntity>) {
        accountDataRepository.update(accountParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeBind(accountParamProvider: AccountParamProvider, subscriber: Observer<AccountEntity>) {
        accountDataRepository.bind(accountParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeUnbind(accountParamProvider: AccountParamProvider, subscriber: Observer<AccountEntity>) {
        accountDataRepository.unbind(accountParamProvider)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .subscribe(subscriber)
    }

    fun executeLogout() {
        accountDataRepository.logout()
    }
}