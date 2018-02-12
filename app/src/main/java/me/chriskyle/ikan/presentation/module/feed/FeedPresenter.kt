package me.chriskyle.ikan.presentation.module.feed

import android.os.Bundle
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface FeedPresenter : MvpLcePresenter<FeedView> {

    fun dispatchArguments(arguments: Bundle?)

    fun feedItemClick(position: Int, feed: FeedEntity)
}