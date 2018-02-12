package me.chriskyle.ikan.presentation.module.feed.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import butterknife.BindView
import butterknife.OnClick
import butterknife.OnTextChanged
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceFragment
import me.chriskyle.ikan.presentation.module.feed.OnFeedItemClickListener
import me.chriskyle.library.design.SearchEditText
import me.chriskyle.library.toolkit.utils.InputMethodUtils
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class SearchFragment : LceFragment<SearchView, SearchPresenter>(), SearchView, OnFeedItemClickListener {

    @BindView(R.id.search_view)
    @JvmField
    internal var searchEditText: SearchEditText? = null

    @Inject
    lateinit var searchAdapter: SearchAdapter
    @Inject
    override lateinit var presenter: SearchPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        InputMethodUtils.showSoftInput(searchEditText)
    }

    override val layout: Int
        get() = R.layout.fragment_search

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().searchComponent(SearchModule()).inject(this)
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        super.initView()

        refreshLayout?.isEnableRefresh = false
        refreshLayout!!.refreshHeader!!.setPrimaryColors(ContextCompat.getColor(context!!, R.color.white))
        searchEditText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodUtils.hideSoftInput(activity)
                presenter.search(searchEditText?.text.toString())
                true
            } else {
                false
            }
        }
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        searchAdapter?.onFeedItemClickListener = this
        recyclerView?.adapter = searchAdapter
    }

    override fun renderFeeds(feeds: List<FeedEntity>) {
        searchAdapter.setFeeds(feeds)
    }

    override fun showSearchContentIsEmpty() {
        showMessage(R.string.search_input_content_is_empty)
    }

    @OnClick(R.id.search_back)
    fun back() {
        InputMethodUtils.hideSoftInput(searchEditText)
        activity!!.onBackPressed()
    }

    @OnTextChanged(R.id.search_view, callback = OnTextChanged.Callback.TEXT_CHANGED)
    fun searchViewTextChanged() {
        presenter.searchViewTextChanged()
    }

    override fun onFeedItemClick(position: Int, feed: FeedEntity) {
        presenter.searchFeedItemClick(feed)
    }
}