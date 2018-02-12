package me.chriskyle.library.mvp

import java.lang.ref.WeakReference

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
abstract class MvpBasePresenter<V : MvpView> : MvpPresenter<V> {

    private var viewRef: WeakReference<V>? = null

    override var view: V?
        set(value) {
            if (value != null) {
                viewRef = WeakReference(value)

                onViewAttached()
            } else {
                onViewDeAttached()

                viewRef?.clear()
                viewRef = null
            }
        }
        get() = viewRef?.get()

    abstract fun onViewAttached()

    abstract fun onViewDeAttached()
}
