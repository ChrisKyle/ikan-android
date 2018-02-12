package me.chriskyle.ikan.presentation.module.account.bind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.OnClick
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import me.chriskyle.library.design.dialog.AlertDialogFragment
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AccountBindActivity : BaseActivity<AccountBindView, AccountBindPresenter>(), AccountBindView {

    @Inject
    override lateinit var presenter: AccountBindPresenter

    override val layout: Int
        get() = R.layout.activity_account_bind

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().accountBindComponent(AccountBindModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.setting_account_bind)
    }

    override fun showUnbindQQDialog() {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setMessage(getString(R.string.unbind_qq_message))
                .setPositive(R.string.confirm)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { whichDialog, view ->
                    if (view.id == R.id.action0) {
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    override fun showUnbindWXDialog() {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setMessage(getString(R.string.unbind_wx_message))
                .setPositive(R.string.confirm)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { whichDialog, view ->
                    if (view.id == R.id.action0) {
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    override fun showUnbindWBDialog() {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setMessage(getString(R.string.unbind_wb_message))
                .setPositive(R.string.confirm)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { whichDialog, view ->
                    if (view.id == R.id.action0) {
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    @OnClick(R.id.qq)
    fun qqBind() {
        presenter.qqBind()
    }

    @OnClick(R.id.wx)
    fun wxBind() {
        presenter.wxBind()
    }

    @OnClick(R.id.wb)
    fun wbBind() {
        presenter.wbBind()
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, AccountBindActivity::class.java)
    }
}