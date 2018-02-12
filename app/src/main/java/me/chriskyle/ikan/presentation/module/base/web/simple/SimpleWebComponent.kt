package me.chriskyle.ikan.presentation.module.base.web.simple

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [SimpleWebModule::class])
interface SimpleWebComponent {

    fun inject(policyActivity: SimpleWebActivity)
}