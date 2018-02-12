package me.chriskyle.ikan.presentation.module.account.edit.nickname

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView
import butterknife.OnTextChanged
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.account.edit.AccountEditPresenterImpl.Companion.EXTRA_KEY_NICKNAME
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class NicknameEditActivity : BaseActivity<NicknameEditView, NicknameEditPresenter>(), NicknameEditView {

    @BindView(R.id.right_icon)
    @JvmField
    internal var rightIcon: ImageView? = null

    @BindView(R.id.nickname)
    @JvmField
    var nickname: EditText? = null

    @Inject
    override lateinit var presenter: NicknameEditPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadAccount()
    }

    override val layout: Int
        get() = R.layout.activity_nickname_edit

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().nicknameEditComponent(NicknameEditModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.nickname_edit)
        rightIcon?.setBackgroundResource(R.drawable.send)
        rightIcon?.isEnabled = false
    }

    override fun renderNicknameHint(nickname: String) {
        this.nickname?.hint = nickname
    }

    override fun enableSend() {
        rightIcon?.isEnabled = true
        rightIcon?.setOnClickListener {
            val intent = Intent()
            intent.putExtra(EXTRA_KEY_NICKNAME, nickname?.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun disableSend() {
        rightIcon?.isEnabled = false
    }

    @OnTextChanged(R.id.nickname, callback = OnTextChanged.Callback.TEXT_CHANGED)
    fun nicknameEditTextChanged(char: CharSequence) {
        presenter.nicknameEditTextChanged(char)
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, NicknameEditActivity::class.java)
    }
}