package me.chriskyle.ikan.presentation.module.help

import android.text.TextUtils
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.FuncParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.FuncRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class HelpPresenterImpl(private val funcRepositoryManager: FuncRepositoryManager) :
        BasePresenter<HelpView>(), HelpPresenter {

    override fun sendFeedback() {
        Router.routeToFeedback(view?.ctx)
    }

    override fun loadHotProblems() {
        view?.showLoadingView()
        funcRepositoryManager.executeGetHotProblems(FuncParamProvider(), LoadHotProblemsObserver())
    }

    override fun problemDetail(problem: ProblemEntity) {
        if (!TextUtils.isEmpty(problem.url)) {
            Router.routeToSimpleWeb(view!!.ctx,problem.url,view!!.ctx.getString(R.string.help_hot_problem))
        }
    }

    override fun refresh() {
        funcRepositoryManager.executeGetHotProblems(FuncParamProvider(), LoadHotProblemsObserver())
    }

    override fun loadmore() {
    }

    override fun reload() {
        loadHotProblems()
    }

    fun loadHotProblemsSuccess(pagerEntity: PagerEntity<List<ProblemEntity>>) {
        view?.let {
            view?.showRefreshCompleted()
            view?.showContent()

            if (pagerEntity.list != null && pagerEntity.list!!.isNotEmpty()) {
                view?.renderProblems(pagerEntity.list!!)
            }
        }
    }

    fun loadHotProblemsError(e: Throwable) {
        view?.let {
            view?.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    inner class LoadHotProblemsObserver : DefaultObserver<PagerEntity<List<ProblemEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<ProblemEntity>>) {
            loadHotProblemsSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadHotProblemsError(e)
        }
    }
}