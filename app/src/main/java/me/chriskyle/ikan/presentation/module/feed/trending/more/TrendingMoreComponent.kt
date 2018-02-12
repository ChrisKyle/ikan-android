package me.chriskyle.ikan.presentation.module.feed.trending.more

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [TrendingMoreModule::class])
interface TrendingMoreComponent {

    fun inject(trendingMoreActivity: TrendingMoreActivity)
}