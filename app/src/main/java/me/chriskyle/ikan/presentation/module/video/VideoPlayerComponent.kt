package me.chriskyle.ikan.presentation.module.video

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [VideoPlayerModule::class])
interface VideoPlayerComponent {

    fun inject(downloadActivity: VideoPlayerActivity)
}