package  me.chriskyle.library.mvp.delegate

import android.os.Bundle
import me.chriskyle.library.mvp.MvpActivity
import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
class ActivityMvpDelegateImpl<V : MvpView, in P : MvpPresenter<V>>
(private val activity: MvpActivity<V, P>, private val delegateCallback: MvpDelegateCallback<V, P>) :
        ActivityMvpDelegate<V, P> {

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(bundle: Bundle?) {
        delegateCallback.presenter.view = activity as V
    }

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onSaveInstanceState(outState: Bundle?) {}

    override fun onStop() {
    }

    override fun onRestart() {
    }

    override fun onDestroy() {
        delegateCallback.presenter.view = null
    }

    override fun onContentChanged() {}

    override fun onPostCreate(savedInstanceState: Bundle?) {}
}
