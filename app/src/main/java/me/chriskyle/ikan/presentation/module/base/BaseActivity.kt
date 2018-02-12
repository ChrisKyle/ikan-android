package me.chriskyle.ikan.presentation.module.base

import android.os.Bundle
import android.support.v4.util.LongSparseArray
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.IKanApplication
import me.chriskyle.ikan.presentation.di.component.ActivityComponent
import me.chriskyle.ikan.presentation.di.component.ConfigPersistentComponent
import me.chriskyle.ikan.presentation.di.component.DaggerConfigPersistentComponent
import me.chriskyle.ikan.presentation.di.module.ActivityModule
import me.chriskyle.library.design.tip.Tip
import me.chriskyle.library.mvp.MvpActivity
import me.chriskyle.library.mvp.MvpPresenter
import me.chriskyle.library.mvp.MvpView
import me.chriskyle.library.toolkit.log.Logger
import me.chriskyle.library.toolkit.utils.ActivityManager
import java.util.concurrent.atomic.AtomicLong

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
abstract class BaseActivity<V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>() {

    @BindView(R.id.title)
    @JvmField
    var title: TextView? = null

    @BindView(R.id.back)
    @JvmField
    var back: ImageView? = null

    private var activityComponent: ActivityComponent? = null
    private var activityId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjection(savedInstanceState)

        super.onCreate(savedInstanceState)

        ActivityManager.getInstance().addActivity(this)

        setContentView(layout)
        ButterKnife.bind(this)
        initView()
    }

    abstract val layout: Int

    open fun initInjection(savedInstanceState: Bundle?) {
        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (componentsArray.get(activityId) == null) {
            Logger.i("Creating new ConfigPersistentComponent id=%d", activityId)

            configPersistentComponent = DaggerConfigPersistentComponent
                    .builder()
                    .applicationComponent(IKanApplication[this].component)
                    .build()
            componentsArray.put(activityId, configPersistentComponent)
        } else {
            Logger.i("Reusing ConfigPersistentComponent id=%d", activityId)

            configPersistentComponent = componentsArray.get(activityId)
        }
        activityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
    }

    open fun initView() {
        back?.setOnClickListener {
            back()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, activityId)
    }

    override fun onDestroy() {
        ActivityManager.getInstance().removeActivity(this)

        if (!isChangingConfigurations) {
            Logger.i("Clearing ConfigPersistentComponent id=%d", activityId)

            componentsArray.remove(activityId)
        }
        super.onDestroy()
    }

    fun activityComponent() = activityComponent as ActivityComponent

    fun showMessage(msgString: String) {
        Tip.showTip(application, msgString)
    }

    fun showMessage(msgResId: Int) {
        showMessage(getString(msgResId))
    }

    open fun back() {
        super.onBackPressed()
    }

    companion object {

        private const val KEY_ACTIVITY_ID = "activityId"

        private val NEXT_ID = AtomicLong(0)
        private val componentsArray = LongSparseArray<ConfigPersistentComponent>()
    }
}
