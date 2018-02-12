package me.chriskyle.ikan.presentation.module.account.login

import android.os.Bundle
import butterknife.BindView
import butterknife.OnClick
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseFragment
import me.chriskyle.library.support.InsLoadingView
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class LoginFragment : BaseFragment<LoginView, LoginPresenter>(), LoginView {

    @BindView(R.id.qq)
    @JvmField
    var qq: InsLoadingView? = null

    @BindView(R.id.wx)
    @JvmField
    var wx: InsLoadingView? = null

    @BindView(R.id.wb)
    @JvmField
    var wb: InsLoadingView? = null

    @Inject
    override lateinit var presenter: LoginPresenter

    override val layout: Int
        get() = R.layout.fragment_login

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().loginComponent(LoginModule()).inject(this)
    }

    override fun onResume() {
        super.onResume()

        presenter.resume()
    }

    override fun renderQQLoginStatus(status: InsLoadingView.Status) {
        qq?.status = status
    }

    override fun renderWXLoginStatus(status: InsLoadingView.Status) {
        wx?.status = status
    }

    override fun renderWBLoginStatus(status: InsLoadingView.Status) {
        wb?.status = status
    }

    override fun showLoginSuccess() {
        back()
    }

    @OnClick(R.id.qq)
    fun qqLogin() {
        presenter.qqLogin()
    }

    @OnClick(R.id.wx)
    fun wxLogin() {
        presenter.wxLogin()
    }

    @OnClick(R.id.wb)
    fun wbLogin() {
        presenter.wbLogin()
    }

    @OnClick(R.id.back)
    fun back() {
        activity?.onBackPressed()
    }
}