package me.chriskyle.ikan.presentation.module.account.bind

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [AccountBindModule::class])
interface AccountBindComponent {

    fun inject(accountBindActivity: AccountBindActivity)
}