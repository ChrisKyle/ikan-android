package me.chriskyle.ikan.domain

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/20.
 */
open class DefaultObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {}

    override fun onNext(t: T) {}

    override fun onError(e: Throwable) {}

    override fun onComplete() {}
}
