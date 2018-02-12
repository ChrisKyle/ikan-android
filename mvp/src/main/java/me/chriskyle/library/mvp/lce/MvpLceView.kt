package me.chriskyle.library.mvp.lce

import android.support.annotation.UiThread

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/28.
 */
interface MvpLceView : MvpView {

    @UiThread
    fun showRefreshCompleted()

    @UiThread
    fun showLoadMoreCompleted()

    @UiThread
    fun showContent()

    @UiThread
    fun showLoadingView()

    @UiThread
    fun showEmptyView()

    @UiThread
    fun showErrorView(errorMsg: String?)

    @UiThread
    fun showNoMoreData()

    @UiThread
    fun showStatusMsg(msg: String)

    @UiThread
    fun loadData(pullToRefresh: Boolean)
}
