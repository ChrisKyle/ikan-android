package me.chriskyle.ikan.presentation.module.feed.search

import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface SearchPresenter : MvpLcePresenter<SearchView> {

    fun search(content: String)

    fun searchViewTextChanged()

    fun searchFeedItemClick(feedEntity: FeedEntity)
}