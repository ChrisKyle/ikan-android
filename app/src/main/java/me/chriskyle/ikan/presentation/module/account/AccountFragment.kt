package me.chriskyle.ikan.presentation.module.account

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.facebook.drawee.view.SimpleDraweeView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseFragment
import me.chriskyle.ikan.presentation.module.main.OnLoginCallback
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AccountFragment : BaseFragment<AccountView, AccountPresenter>(), AccountView {

    @BindView(R.id.title)
    @JvmField
    internal var title: TextView? = null

    @BindView(R.id.back)
    @JvmField
    internal var back: ImageView? = null

    @BindView(R.id.account_avatar)
    @JvmField
    internal var accountAvatar: SimpleDraweeView? = null

    @BindView(R.id.gender)
    @JvmField
    var gender: ImageView? = null

    @BindView(R.id.nickname)
    @JvmField
    var nickname: TextView? = null

    @BindView(R.id.account_edit)
    @JvmField
    var accountEdit: ImageView? = null

    @BindView(R.id.nickname_lay)
    @JvmField
    var nicknameLay: View? = null

    @BindView(R.id.account_assets)
    @JvmField
    var accountAssets: View? = null

    @BindView(R.id.balance)
    @JvmField
    var balance: TextView? = null

    @BindView(R.id.un_login_tip)
    @JvmField
    var unLoginTip: TextView? = null

    @BindView(R.id.login)
    @JvmField
    internal var loginBtn: Button? = null

    @Inject
    override lateinit var presenter: AccountPresenter

    private var onLoginCallback: OnLoginCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onLoginCallback = context as OnLoginCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.checkLoginStatus()
    }

    override val layout: Int
        get() = R.layout.fragment_account

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().accountComponent(AccountModule()).inject(this)
    }

    override fun initView() {
        back?.setImageResource(R.drawable.ic_close)
        title?.text = getString(R.string.account)
    }

    override fun renderAvatar(avatarUrl: String?) {
        if (avatarUrl == null) {
            accountAvatar?.setActualImageResource(R.drawable.ic_avatar_login)
        } else {
            accountAvatar?.setImageURI(avatarUrl)
        }
    }

    override fun renderNickname(nickname: String?) {
        this.nickname?.text = nickname
    }

    override fun renderGender(gender: String) {
        if (TextUtils.equals(getString(R.string.account_edit_sex_male), gender)) {
            this.gender?.setImageResource(R.drawable.ic_sex_male)
        } else {
            this.gender?.setImageResource(R.drawable.ic_sex_female)
        }
    }

    override fun renderBalance(balance: Long) {
        this.balance?.text = String.format(getString(R.string.account_balance), balance)
    }

    override fun showLoggedStatus() {
        loginBtn?.visibility = View.GONE
        nicknameLay?.visibility = View.VISIBLE
        accountAssets?.visibility = View.VISIBLE
        gender?.visibility = View.VISIBLE
        accountEdit?.visibility = View.VISIBLE
        balance?.visibility = View.VISIBLE
        unLoginTip?.visibility = View.GONE
    }

    override fun showUnLoginStatus() {
        loginBtn?.visibility = View.VISIBLE
        nicknameLay?.visibility = View.GONE
        accountAssets?.visibility = View.GONE
        gender?.visibility = View.GONE
        accountEdit?.visibility = View.GONE
        balance?.visibility = View.GONE
        unLoginTip?.visibility = View.VISIBLE
    }

    @OnClick(R.id.back)
    fun back() {
        activity!!.onBackPressed()
    }

    @OnClick(R.id.account_edit)
    fun accountEdit() {
        presenter.accountEdit()
    }

    @OnClick(R.id.login)
    fun login() {
        onLoginCallback?.onOnLogin()
    }

    @OnClick(R.id.account_notification)
    fun notification() {
        presenter.notification()
    }

    @OnClick(R.id.account_assets)
    fun assets() {
        presenter.assets()
    }

    @OnClick(R.id.account_setting)
    fun setting() {
        presenter.setting()
    }

    @OnClick(R.id.watch_history)
    fun watchHistory() {
        presenter.watchHistory()
    }

    @OnClick(R.id.download)
    fun download() {
        presenter.download()
    }

    @OnClick(R.id.upload)
    fun upload() {
        presenter.upload()
    }
}