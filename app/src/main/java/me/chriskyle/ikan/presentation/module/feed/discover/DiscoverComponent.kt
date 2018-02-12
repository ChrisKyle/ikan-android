package me.chriskyle.ikan.presentation.module.feed.discover

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [DiscoverModule::class])
interface DiscoverComponent {

    fun inject(discoverFragment: DiscoverFragment)
}