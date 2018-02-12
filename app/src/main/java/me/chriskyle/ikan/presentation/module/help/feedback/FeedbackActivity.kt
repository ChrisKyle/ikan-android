package me.chriskyle.ikan.presentation.module.help.feedback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView
import butterknife.OnTextChanged
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class FeedbackActivity : BaseActivity<FeedbackView, FeedbackPresenter>(), FeedbackView {

    @BindView(R.id.right_icon)
    @JvmField
    internal var rightIcon: ImageView? = null

    @BindView(R.id.feedback_edit_text)
    @JvmField
    internal var content: EditText? = null

    @Inject
    override lateinit var presenter: FeedbackPresenter

    override val layout: Int
        get() = R.layout.activity_feedback

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().feedbackComponent(FeedbackModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.feedback)
        rightIcon?.setBackgroundResource(R.drawable.send)
        rightIcon?.isEnabled = false

        rightIcon?.setOnClickListener {
            presenter.sendFeedback(content!!.text.toString())
        }
    }

    override fun enableSend() {
        rightIcon?.isEnabled = true
    }

    override fun disableSend() {
        rightIcon?.isEnabled = false
    }

    override fun showSendFeedbackSuccess() {
        showMessage(getString(R.string.send_feedback_success))
        onBackPressed()
    }

    override fun showSendFeedbackError(msg: String) {
        showMessage(msg)
    }

    @OnTextChanged(R.id.feedback_edit_text, callback = OnTextChanged.Callback.TEXT_CHANGED)
    fun feedbackEditTextChanged(char: CharSequence) {
        presenter.feedbackEditTextChanged(char)
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, FeedbackActivity::class.java)
    }
}