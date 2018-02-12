package me.chriskyle.ikan.presentation.module.feed.upload

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import butterknife.BindView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class UploadActivity : BaseActivity<UploadView, UploadPresenter>(), UploadView {

    @BindView(R.id.right_icon)
    @JvmField
    internal var rightIcon: ImageView? = null

    @Inject
    override lateinit var presenter: UploadPresenter

    override val layout: Int
        get() = R.layout.activity_upload

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().uploadComponent(UploadModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_upload)
        rightIcon?.setBackgroundResource(R.drawable.send)
        rightIcon?.isEnabled = false
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, UploadActivity::class.java)
    }
}