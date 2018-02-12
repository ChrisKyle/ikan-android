package me.chriskyle.ikan.presentation.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.presentation.di.ActivityContext

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return activity.baseContext
    }
}
