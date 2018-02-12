package me.chriskyle.ikan.presentation.module.help.feedback

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [FeedbackModule::class])
interface FeedbackComponent {

    fun inject(feedbackActivity: FeedbackActivity)
}