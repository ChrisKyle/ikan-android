package me.chriskyle.ikan.presentation.module.feed.home

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)
}