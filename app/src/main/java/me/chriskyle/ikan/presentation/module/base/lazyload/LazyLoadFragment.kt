package me.chriskyle.ikan.presentation.module.base.lazyload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.chriskyle.ikan.presentation.module.base.lce.LceFragment
import me.chriskyle.library.mvp.lce.MvpLcePresenter
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/13.
 */
abstract class LazyLoadFragment<V : MvpLceView, P : MvpLcePresenter<V>> : LceFragment<V, P>() {

    private var isInit = false
    private var isLoaded = false
    private var contentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState)
        isInit = true

        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCanLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        isCanLoadData()
    }

    private fun isCanLoadData() {
        if (!isInit) {
            return
        }

        if (userVisibleHint) {
            if (!isLoaded) {
                lazyLoad()
                isLoaded = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        isInit = false
        isLoaded = false
    }

    protected abstract fun lazyLoad()
}
