package me.chriskyle.ikan.presentation.module.about

import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AboutPresenter : MvpPresenter<AboutView> {

    fun checkCurrentVersion()

    fun checkVersionUpdate()

    fun versionUpdate()

    fun share()

    fun feedback()
}