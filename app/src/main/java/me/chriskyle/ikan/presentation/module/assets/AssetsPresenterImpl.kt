package me.chriskyle.ikan.presentation.module.assets

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.entity.DenominationEntity
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.AssetsRepositoryManager
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AssetsPresenterImpl @Inject constructor(private val assetsRepositoryManager: AssetsRepositoryManager,
                                              private val accountRepositoryManager: AccountRepositoryManager) :
        BasePresenter<AssetsView>(), AssetsPresenter {

    lateinit var account: AccountEntity

    override fun loadBalance() {
        val account = accountRepositoryManager.executeGetLogged()
        account?.let {
            view?.renderBalance(account.balance)
        }
    }

    override fun loadDenominations() {
        assetsRepositoryManager.executeGetDenominations(LoadDenominationsObserver())
    }

    override fun denominationChanged(denomination: String) {
        view?.enableDonate()
    }

    override fun donate() {
    }

    fun loadDenominationsSuccess(denominationEntities: List<DenominationEntity>) {
        view?.let {
            val denominations = mutableListOf<String>()
            denominationEntities.iterator().forEach {
                denominations.add(String.format(view!!.ctx.getString(R.string.denomination_unit), it.amount))
            }

            view!!.renderDenominations(denominations)
        }
    }

    fun loadDenominationsError(e: Throwable) {}

    inner class LoadDenominationsObserver : DefaultObserver<List<DenominationEntity>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: List<DenominationEntity>) {
            loadDenominationsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadDenominationsError(e)
        }
    }
}