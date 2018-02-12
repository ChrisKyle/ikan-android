package me.chriskyle.ikan.presentation.module.feed.recommend

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [RecommendModule::class])
interface RecommendComponent {

    fun inject(seeListFragment: RecommendFragment)
}