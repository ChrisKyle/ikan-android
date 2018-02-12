package me.chriskyle.ikan.presentation.module.base

import android.os.Bundle
import android.support.v4.util.LongSparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import me.chriskyle.ikan.presentation.IKanApplication
import me.chriskyle.ikan.presentation.di.component.ConfigPersistentComponent
import me.chriskyle.ikan.presentation.di.component.DaggerConfigPersistentComponent
import me.chriskyle.ikan.presentation.di.component.FragmentComponent
import me.chriskyle.ikan.presentation.di.module.FragmentModule
import me.chriskyle.library.design.tip.Tip
import me.chriskyle.library.mvp.MvpFragment
import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView
import me.chriskyle.library.toolkit.log.Logger
import java.util.concurrent.atomic.AtomicLong

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
abstract class BaseFragment<V : MvpView, P : MvpPresenter<V>> : MvpFragment<V, P>() {

    private var fragmentComponent: FragmentComponent? = null
    private var fragmentId: Long = 0L

    abstract override var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInjection(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View? = inflater.inflate(layout, container, false)
        ButterKnife.bind(this, view as View)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    open fun initInjection(savedInstanceState: Bundle?) {
        fragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (componentsArray.get(fragmentId) == null) {
            Logger.i("Creating new ConfigPersistentComponent id=%d", fragmentId)

            configPersistentComponent = DaggerConfigPersistentComponent
                    .builder()
                    .applicationComponent(IKanApplication[activity!!].component)
                    .build()
            componentsArray.put(fragmentId, configPersistentComponent)
        } else {
            Logger.i("Reusing ConfigPersistentComponent id=%d", fragmentId)

            configPersistentComponent = componentsArray.get(fragmentId)
        }
        fragmentComponent = configPersistentComponent.fragmentComponent(FragmentModule(this))
    }

    abstract val layout: Int

    open fun initView() {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, fragmentId)
    }

    override fun onDestroy() {
        if (!activity!!.isChangingConfigurations) {
            Logger.i("Clearing ConfigPersistentComponent id=%d", fragmentId)

            componentsArray.remove(fragmentId)
        }
        super.onDestroy()
    }

    fun fragmentComponent() = fragmentComponent as FragmentComponent

    fun showMessage(msgString: String) {
        Tip.showTip(context, msgString)
    }

    fun showMessage(msgStringResId: Int) {
        showMessage(getString(msgStringResId))
    }

    companion object {

        private const val KEY_FRAGMENT_ID = "fragmentId"
        private val componentsArray = LongSparseArray<ConfigPersistentComponent>()
        private val NEXT_ID = AtomicLong(0)
    }
}
