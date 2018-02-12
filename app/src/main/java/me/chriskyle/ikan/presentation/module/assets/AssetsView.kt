package me.chriskyle.ikan.presentation.module.assets

import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AssetsView : MvpView {

    fun renderBalance(balance: Long)

    fun renderDenominations(denominations: MutableList<String>)

    fun enableDonate()
}