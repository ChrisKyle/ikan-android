package me.chriskyle.ikan.presentation.module.account

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [AccountModule::class])
interface AccountComponent {

    fun inject(accountFragment: AccountFragment)
}