package me.chriskyle.ikan.presentation.module.feed.discover

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.view.View
import android.widget.ViewFlipper
import butterknife.BindView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.base.BaseFragment
import me.chriskyle.library.design.lceview.AbsLceView
import me.chriskyle.library.design.lceview.EmptyLceView
import me.chriskyle.library.design.lceview.ErrorLceView
import me.chriskyle.library.support.recyclerview.cardview.CardConfig
import me.chriskyle.library.support.recyclerview.cardview.CardItemTouchHelperCallback
import me.chriskyle.library.support.recyclerview.cardview.CardLayoutManager
import me.chriskyle.library.support.recyclerview.cardview.OnSwipeListener
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class DiscoverFragment : BaseFragment<DiscoverView, DiscoverPresenter>(), DiscoverView,
        AbsLceView.OnLceActionListener {

    @BindView(R.id.lce_container)
    @JvmField
    internal var lceContainer: ViewFlipper? = null

    @BindView(R.id.error_view)
    @JvmField
    internal var errorLceView: ErrorLceView? = null

    @BindView(R.id.empty_view)
    @JvmField
    internal var emptyLceView: EmptyLceView? = null

    @BindView(R.id.recycler_view)
    @JvmField
    internal var recyclerView: RecyclerView? = null

    @Inject
    override lateinit var presenter: DiscoverPresenter
    @Inject
    lateinit var movieFeedAdapter: DiscoverFeedAdapter

    override val layout: Int
        get() = R.layout.fragment_discover

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadFeeds()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().discoverComponent(DiscoverModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        errorLceView?.setOnLceActionListener(this)
        emptyLceView?.setOnLceActionListener(this)

        recyclerView?.adapter = movieFeedAdapter
    }

    override fun renderFeeds(feeds: List<FeedEntity>) {
        movieFeedAdapter.setFeeds(feeds)

        val cardCallback = CardItemTouchHelperCallback(recyclerView!!.adapter, feeds)
        val touchHelper = ItemTouchHelper(cardCallback)
        val cardLayoutManager = CardLayoutManager(recyclerView!!, touchHelper)
        recyclerView!!.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(recyclerView)
        cardCallback.setOnSwipedListener(object : OnSwipeListener<FeedEntity> {

            override fun onSwiping(viewHolder: RecyclerView.ViewHolder, ratio: Float, direction: Int) {
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, t: FeedEntity, direction: Int) {
                if (direction == CardConfig.SWIPED_LEFT) {
                    presenter.feedDetail(t)
                }
            }

            override fun onSwipedClear() {
                presenter.refreshFeeds()
            }
        })
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
            errorLceView!!.setDesText(errorMsg)
        }
    }

    override fun showStatusMsg(errorMsg: String) {
        showMessage(errorMsg)
    }

    override fun onIceActionClick() {
        presenter.reloadFeeds()
    }
}