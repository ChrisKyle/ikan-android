package me.chriskyle.ikan.presentation.module.main

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
interface MainPresenter : MvpPresenter<MainView> {

    fun checkLoginStatus()

    fun checkVersionUpdate()

    fun search()

    fun account()
}
