package me.chriskyle.ikan.presentation.module.help

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [HelpModule::class])
interface HelpComponent {

    fun inject(helpActivity: HelpActivity)
}