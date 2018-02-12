package me.chriskyle.ikan.presentation.module.main

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@Singleton
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}