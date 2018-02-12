package me.chriskyle.ikan.presentation.module.feed.upload

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [UploadModule::class])
interface UploadComponent {

    fun inject(downloadActivity: UploadActivity)
}