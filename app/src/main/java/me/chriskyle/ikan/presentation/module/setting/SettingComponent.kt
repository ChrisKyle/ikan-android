package me.chriskyle.ikan.presentation.module.setting

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [SettingModule::class])
interface SettingComponent {

    fun inject(settingActivity: SettingActivity)
}