package me.chriskyle.ikan.presentation.module.video

import android.content.Intent
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface VideoPlayerPresenter : MvpLcePresenter<VideoPlayerView> {

    fun dispatchIntent(intent: Intent?)
}