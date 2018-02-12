package me.chriskyle.ikan.presentation.module.feed.search

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    fun inject(attentionFragment: SearchFragment)
}