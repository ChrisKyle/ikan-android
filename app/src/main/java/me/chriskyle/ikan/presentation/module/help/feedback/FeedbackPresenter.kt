package me.chriskyle.ikan.presentation.module.help.feedback

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface FeedbackPresenter : MvpPresenter<FeedbackView> {

    fun feedbackEditTextChanged(char: CharSequence)

    fun sendFeedback(content: String)
}