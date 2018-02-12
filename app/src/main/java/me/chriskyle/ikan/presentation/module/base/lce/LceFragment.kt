package me.chriskyle.ikan.presentation.module.base.lce

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.ViewFlipper
import butterknife.BindView
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseFragment
import me.chriskyle.library.design.lceview.AbsLceView
import me.chriskyle.library.design.lceview.EmptyLceView
import me.chriskyle.library.design.lceview.ErrorLceView
import me.chriskyle.library.mvp.lce.MvpLcePresenter
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
abstract class LceFragment<V : MvpLceView, P : MvpLcePresenter<V>> : BaseFragment<V, P>(),
        MvpLceView, OnRefreshListener, OnLoadmoreListener, AbsLceView.OnLceActionListener {

    @BindView(R.id.refresh_layout)
    @JvmField
    internal var refreshLayout: SmartRefreshLayout? = null

    @BindView(R.id.lce_container)
    @JvmField
    internal var lceContainer: ViewFlipper? = null

    @BindView(R.id.recycler_view)
    @JvmField
    internal var recyclerView: RecyclerView? = null

    @BindView(R.id.error_view)
    @JvmField
    internal var errorLceView: ErrorLceView? = null

    @BindView(R.id.empty_view)
    @JvmField
    internal var emptyLceView: EmptyLceView? = null

    override val layout: Int
        get() = R.layout.layout_lce

    @SuppressLint("RestrictedApi")
    override fun initView() {
        super.initView()

        refreshLayout!!.refreshHeader = MaterialHeader(context)
                .setShowBezierWave(true)
                .setColorSchemeColors(ContextCompat.getColor(context!!, R.color.refresh_indicator_color))
        refreshLayout!!.refreshHeader!!.setPrimaryColors(ContextCompat.getColor(context!!, R.color.refresh_header_bg_color))
        refreshLayout!!.refreshFooter = BallPulseFooter(this.activity!!)
                .setSpinnerStyle(SpinnerStyle.Scale)
                .setIndicatorColor(ContextCompat.getColor(context!!, R.color.loadmore_indicator_color))
                .setNormalColor(ContextCompat.getColor(context!!, R.color.loadmore_indicator_color))
                .setAnimatingColor(ContextCompat.getColor(context!!, R.color.loadmore_indicator_color))
        refreshLayout!!.setOnRefreshListener(this)
        refreshLayout!!.setOnLoadmoreListener(this)

        errorLceView?.setOnLceActionListener(this)
        emptyLceView?.setOnLceActionListener(this)
    }

    override fun showRefreshCompleted() {
        refreshLayout!!.finishRefresh()
    }

    override fun showLoadMoreCompleted() {
        refreshLayout!!.finishLoadmore()
    }

    override fun showContent() {
        lceContainer!!.displayedChild = 0
    }

    override fun showLoadingView() {
        lceContainer!!.displayedChild = 1
    }

    override fun showEmptyView() {
        lceContainer!!.displayedChild = 2
    }

    override fun showErrorView(errorMsg: String?) {
        lceContainer!!.displayedChild = 3
        if (!TextUtils.isEmpty(errorMsg)) {
            errorLceView?.setDesText(errorMsg)
        }
    }

    override fun showNoMoreData() {
        refreshLayout!!.isEnableLoadmore = false
    }

    override fun showStatusMsg(msg: String) {
        showMessage(msg)
    }

    override fun loadData(pullToRefresh: Boolean) {
        if (!pullToRefresh) {
            showLoadingView()
        } else {
            refreshLayout!!.autoRefresh()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        presenter.refresh()
    }

    override fun onLoadmore(refreshLayout: RefreshLayout?) {
        presenter.loadmore()
    }

    override fun onIceActionClick() {
        presenter.reload()
    }
}