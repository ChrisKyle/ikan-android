package me.chriskyle.ikan.presentation.module.feed.download

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = arrayOf(DownloadModule::class))
interface DownloadComponent {

    fun inject(downloadActivity: DownloadActivity)
}