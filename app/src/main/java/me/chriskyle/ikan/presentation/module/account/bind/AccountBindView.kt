package me.chriskyle.ikan.presentation.module.account.bind

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AccountBindView : MvpView {

    fun showUnbindQQDialog()

    fun showUnbindWXDialog()

    fun showUnbindWBDialog()
}