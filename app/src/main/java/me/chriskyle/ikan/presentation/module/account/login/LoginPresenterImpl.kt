package me.chriskyle.ikan.presentation.module.account.login

import android.app.Activity
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.AccountEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.AccountParamProvider
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.presentation.event.LoginEvent
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.SocialApi
import me.chriskyle.library.social.listener.AuthListener
import me.chriskyle.library.social.model.UserModel
import me.chriskyle.library.support.InsLoadingView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class LoginPresenterImpl(private val bus: Bus, private val accountRepositoryManager: AccountRepositoryManager)
    : BasePresenter<LoginView>(), LoginPresenter, AuthListener {

    override fun qqLogin() {
        view?.renderQQLoginStatus(InsLoadingView.Status.LOADING)

        SocialApi.instance.doAuthVerify(view?.ctx as Activity, PlatformType.QQ, this)
    }

    override fun wxLogin() {
        view?.renderWXLoginStatus(InsLoadingView.Status.LOADING)

        SocialApi.instance.doAuthVerify(view?.ctx as Activity, PlatformType.WX, this)
    }

    override fun wbLogin() {
        view?.renderWBLoginStatus(InsLoadingView.Status.LOADING)

        SocialApi.instance.doAuthVerify(view?.ctx as Activity, PlatformType.WB, this)
    }

    override fun resume() {
        view?.renderQQLoginStatus(InsLoadingView.Status.CLICKED)
        view?.renderWXLoginStatus(InsLoadingView.Status.CLICKED)
        view?.renderWBLoginStatus(InsLoadingView.Status.CLICKED)
    }

    override fun onAuthComplete(platformType: PlatformType, userModel: UserModel) {
        val accountParamProvider = AccountParamProvider()
        with(accountParamProvider) {
            openId(userModel.openId)
            nickname(userModel.nickname)
            avatar(userModel.avatar)
            gender(userModel.gender)
            bindType(platformType.ordinal)
        }
        accountRepositoryManager.executeLogin(accountParamProvider, LoginObserver())
    }

    override fun onAuthError(platformType: PlatformType, errMsg: String) {
        view?.showLoginSuccess()
    }

    override fun onAuthCancel(platformType: PlatformType) {
        platformType.let {
            when (platformType) {
                PlatformType.QQ -> view?.renderQQLoginStatus(InsLoadingView.Status.UN_CLICKED)
                PlatformType.WX -> view?.renderWXLoginStatus(InsLoadingView.Status.UN_CLICKED)
                PlatformType.WB -> view?.renderWBLoginStatus(InsLoadingView.Status.UN_CLICKED)
                else -> {
                }
            }
        }
    }

    private fun loginSuccess(accountEntity: AccountEntity) {
        view?.showLoginSuccess()
        bus.post(LoginEvent(accountEntity))
    }

    private fun loginError(e: Throwable) {
    }

    inner class LoginObserver : DefaultObserver<AccountEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: AccountEntity) {
            loginSuccess(t)
        }

        override fun onError(e: Throwable) {
            loginError(e)
        }
    }
}