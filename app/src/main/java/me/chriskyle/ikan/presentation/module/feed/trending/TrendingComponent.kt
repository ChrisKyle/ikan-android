package me.chriskyle.ikan.presentation.module.feed.trending

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [TrendingModule::class])
interface TrendingComponent {

    fun inject(tendingFragment: TendingFragment)
}