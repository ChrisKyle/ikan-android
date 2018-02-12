package me.chriskyle.library.mvp.delegate

import android.os.Bundle

import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
interface ActivityMvpDelegate<V : MvpView, in P : MvpPresenter<V>> {

    fun onCreate(bundle: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onSaveInstanceState(outState: Bundle?)

    fun onStop()

    fun onRestart()

    fun onDestroy()

    fun onContentChanged()

    fun onPostCreate(savedInstanceState: Bundle?)
}
