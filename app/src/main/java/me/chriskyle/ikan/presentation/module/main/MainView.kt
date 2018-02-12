package me.chriskyle.ikan.presentation.module.main

import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
interface MainView : MvpView {

    fun showSearchFragment()

    fun showAccountFragment()

    fun showUpdateDialog(versionEntity: VersionEntity)

    fun renderAvatar(avatarUrl: String?)
}