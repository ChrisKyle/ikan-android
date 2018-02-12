package me.chriskyle.ikan.presentation.module.feed.home

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface HomePresenter : MvpPresenter<HomeView> {

    fun loadFeedSegments()
}