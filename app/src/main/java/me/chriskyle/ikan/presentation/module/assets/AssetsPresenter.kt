package me.chriskyle.ikan.presentation.module.assets

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AssetsPresenter : MvpPresenter<AssetsView> {

    fun loadBalance()

    fun loadDenominations()

    fun denominationChanged(denomination : String)

    fun donate()
}