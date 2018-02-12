package me.chriskyle.ikan.presentation.module.assets

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = arrayOf(AssetsModule::class))
interface AssetsComponent {

    fun inject(assetsActivity: AssetsActivity)
}