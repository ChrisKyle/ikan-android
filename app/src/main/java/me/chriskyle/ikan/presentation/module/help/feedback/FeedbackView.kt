package me.chriskyle.ikan.presentation.module.help.feedback

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface FeedbackView : MvpView {

    fun enableSend()

    fun disableSend()

    fun showSendFeedbackSuccess()

    fun showSendFeedbackError(msg: String)
}