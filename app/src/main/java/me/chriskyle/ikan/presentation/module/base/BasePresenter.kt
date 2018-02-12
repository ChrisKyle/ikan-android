package me.chriskyle.ikan.presentation.module.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.chriskyle.library.mvp.MvpBasePresenter
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
abstract class BasePresenter<V : MvpView> : MvpBasePresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onViewAttached() {
    }

    override fun onViewDeAttached() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}

