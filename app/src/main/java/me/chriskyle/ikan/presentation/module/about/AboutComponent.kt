package me.chriskyle.ikan.presentation.module.about

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [AboutModule::class])
interface AboutComponent {

    fun inject(aboutActivity: AboutActivity)
}