package me.chriskyle.ikan.presentation.module.feed.home

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface HomeView : MvpView {

    fun renderFeedSegments(segments: ArrayList<String>)
}