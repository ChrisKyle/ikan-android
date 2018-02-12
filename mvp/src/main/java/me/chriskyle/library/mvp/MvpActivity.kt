package me.chriskyle.library.mvp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.chriskyle.library.mvp.delegate.ActivityMvpDelegate
import me.chriskyle.library.mvp.delegate.ActivityMvpDelegateImpl
import me.chriskyle.library.mvp.delegate.MvpDelegateCallback

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
abstract class MvpActivity<V : MvpView, P : MvpPresenter<V>> : AppCompatActivity()
        , MvpDelegateCallback<V, P>, MvpView {

    private lateinit var mvpDelegate: ActivityMvpDelegate<V, P>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvpDelegate = ActivityMvpDelegateImpl(this, this)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate.onPause()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        mvpDelegate.onRestart()
    }

    override fun onDestroy() {
        mvpDelegate.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        mvpDelegate.onContentChanged()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mvpDelegate.onPostCreate(savedInstanceState)
    }

    final override val ctx: Context
        get() = this
}
