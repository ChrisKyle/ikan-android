package me.chriskyle.library.mvp.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
interface FragmentMvpDelegate<V : MvpView, P : MvpPresenter<V>> {

    fun onAttach(context: Context)

    fun onCreate(saved: Bundle?)

    fun onViewCreated(view: View, savedInstanceState: Bundle?)

    fun onActivityCreated(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onSaveInstanceState(outState: Bundle?)

    fun onStop()

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()
}
