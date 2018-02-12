package me.chriskyle.ikan.presentation.module.feed

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [FeedModule::class])
interface FeedComponent {

    fun inject(movieFeedsFragment: FeedFragment)
}