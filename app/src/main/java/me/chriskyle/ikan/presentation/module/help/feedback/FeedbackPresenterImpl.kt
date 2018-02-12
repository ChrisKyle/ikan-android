package me.chriskyle.ikan.presentation.module.help.feedback

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.repository.datastore.params.provider.FuncParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.FuncRepositoryManager
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class FeedbackPresenterImpl(private val funcRepositoryManager: FuncRepositoryManager) :
        BasePresenter<FeedbackView>(), FeedbackPresenter {

    override fun feedbackEditTextChanged(char: CharSequence) {
        if (char.isNotEmpty()) {
            view?.enableSend()
        } else {
            view?.disableSend()
        }
    }

    override fun sendFeedback(content: String) {
        val paramProvider = FuncParamProvider()
        paramProvider.content(content)
        funcRepositoryManager.executeSendFeedback(paramProvider, SendFeedbackObserver())
    }

    fun sendFeedbackSuccess() {
        view?.showSendFeedbackSuccess()
    }

    fun sendFeedbackError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showSendFeedbackError(msg)
    }

    inner class SendFeedbackObserver : DefaultObserver<Any>() {

        override fun onComplete() {
            sendFeedbackSuccess()
        }

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onError(e: Throwable) {
            sendFeedbackError(e)
        }
    }
}