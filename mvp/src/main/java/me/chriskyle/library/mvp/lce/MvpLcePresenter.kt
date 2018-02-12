package me.chriskyle.library.mvp.lce

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/28.
 */
interface MvpLcePresenter<V : MvpLceView> : MvpPresenter<V> {

    fun refresh()

    fun loadmore()

    fun reload()
}