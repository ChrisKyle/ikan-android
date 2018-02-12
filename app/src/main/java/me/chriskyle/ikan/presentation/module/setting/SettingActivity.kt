package me.chriskyle.ikan.presentation.module.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.OnCheckedChanged
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
class SettingActivity : BaseActivity<SettingView, SettingPresenter>(), SettingView {

    @BindView(R.id.notification)
    @JvmField
    var notification: SwitchCompat? = null

    @BindView(R.id.account_bind)
    @JvmField
    var accountBind: View? = null

    @BindView(R.id.logout)
    @JvmField
    var logout: Button? = null

    @Inject
    override lateinit var presenter: SettingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.checkNotificationStatus()
        presenter.checkAccountStatus()
    }

    override val layout: Int
        get() = R.layout.activity_setting

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().settingComponent(SettingModule(this)).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_setting)
    }

    override fun renderNotificationStatus(isOpen: Boolean) {
        notification?.isChecked = isOpen
    }

    override fun showClearDownloadHistoryDialog() {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setMessage(getString(R.string.clear_download_history_alert_message))
                .setPositive(R.string.confirm)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { _, view ->
                    if (view.id == R.id.action0) {
                        presenter.confirmClearDownloadHistory()
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    override fun showClearPlayHistoryDialog() {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setMessage(getString(R.string.clear_watch_history_alert_message))
                .setPositive(R.string.confirm)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { _, view ->
                    if (view.id == R.id.action0) {
                        presenter.confirmClearPlayHistory()
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    override fun showLogoutDialog() {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setMessage(getString(R.string.confirm_logout_alert_message))
                .setPositive(R.string.confirm)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { _, view ->
                    if (view.id == R.id.action0) {
                        presenter.confirmLogout()
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    override fun showLoggedStatus() {
        logout?.visibility = View.VISIBLE
    }

    override fun showUnLoginStatus() {
        logout?.visibility = View.GONE
    }

    override fun showClearWatchHistorySuccess() {
        showMessage(R.string.clear_watch_history_success)
    }

    override fun showLogoutSuccess() {
        showMessage(R.string.logout_success)
    }

    @OnCheckedChanged(R.id.notification)
    fun notification(checked: Boolean) {
        if (checked)
            presenter.openNotification()
        else
            presenter.closeNotification()
    }

    @OnClick(R.id.account_bind)
    fun accountBind() {
        presenter.accountBind()
    }

    @OnClick(R.id.clear_download_history)
    fun clearDownloadHistory() {
        presenter.clearDownloadHistory()
    }

    @OnClick(R.id.clear_play_history)
    fun clearPlayHistory() {
        presenter.clearPlayHistory()
    }

    @OnClick(R.id.help_feedback)
    fun helpFeedback() {
        presenter.help()
    }

    @OnClick(R.id.privacy_policy)
    fun privacyPolicy() {
        presenter.privacyPolicy()
    }

    @OnClick(R.id.about)
    fun about() {
        presenter.about()
    }

    @OnClick(R.id.logout)
    fun logout() {
        presenter.logout()
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, SettingActivity::class.java)
    }
}