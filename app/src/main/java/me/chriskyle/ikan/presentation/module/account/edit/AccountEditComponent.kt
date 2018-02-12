package me.chriskyle.ikan.presentation.module.account.edit

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [AccountEditModule::class])
interface AccountEditComponent {

    fun inject(accountActivity: AccountEditActivity)
}