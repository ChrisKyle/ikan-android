package me.chriskyle.ikan.presentation.module.feed.download

import dagger.Module
import dagger.Provides

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class DownloadModule {

    @Provides
    fun provideDownloadPresenter(): DownloadPresenter {
        return DownloadPresenterImpl()
    }
}