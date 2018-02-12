package me.chriskyle.ikan.presentation.module.account.edit.nickname

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [NicknameEditModule::class])
interface NicknameEditComponent {

    fun inject(nicknameEditActivity: NicknameEditActivity)
}