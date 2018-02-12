package me.chriskyle.ikan.presentation.module.video

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class VideoPlayerModule {

    @Provides
    fun provideVideoPlayerPresenter(): VideoPlayerPresenter = VideoPlayerPresenterImpl()
}