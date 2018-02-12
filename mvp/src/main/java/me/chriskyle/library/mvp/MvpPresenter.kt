package me.chriskyle.library.mvp

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
interface MvpPresenter<V : MvpView> {

    var view: V?
}
