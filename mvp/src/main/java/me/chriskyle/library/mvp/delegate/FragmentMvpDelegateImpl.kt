package me.chriskyle.library.mvp.delegate

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
class FragmentMvpDelegateImpl<V : MvpView, P : MvpPresenter<V>>
(var fragment: Fragment, private val delegateCallback: MvpDelegateCallback<V, P>) :
        FragmentMvpDelegate<V, P> {

    override fun onAttach(context: Context) {}

    override fun onCreate(saved: Bundle?) {
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        delegateCallback.presenter.view = fragment as V
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onSaveInstanceState(outState: Bundle?) {
    }

    override fun onStart() {
    }

    override fun onStop() {}

    override fun onDestroyView() {
        delegateCallback.presenter.view = null
    }

    override fun onDestroy() {}

    override fun onDetach() {}
}
