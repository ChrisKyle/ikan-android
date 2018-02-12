package me.chriskyle.ikan.presentation.di.component

import dagger.Component
import me.chriskyle.ikan.presentation.di.ConfigPersistent
import me.chriskyle.ikan.presentation.di.module.ActivityModule
import me.chriskyle.ikan.presentation.di.module.FragmentModule

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@ConfigPersistent
@Component(dependencies = [ApplicationComponent::class])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent
}
