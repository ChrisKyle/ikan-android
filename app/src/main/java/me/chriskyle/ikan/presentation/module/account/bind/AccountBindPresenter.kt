package me.chriskyle.ikan.presentation.module.account.bind

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AccountBindPresenter : MvpPresenter<AccountBindView> {

    fun qqBind()

    fun wxBind()

    fun wbBind()
}