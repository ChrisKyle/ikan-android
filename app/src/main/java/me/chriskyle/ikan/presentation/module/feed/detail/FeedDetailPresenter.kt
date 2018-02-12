package me.chriskyle.ikan.presentation.module.feed.detail

import android.content.Intent
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface FeedDetailPresenter : MvpLcePresenter<FeedDetailView> {

    fun dispatchIntent(intent: Intent)

    fun initDownloader()

    fun checkLoginStatus()

    fun like()

    fun dislike()

    fun download()

    fun share()

    fun preparePlay()

    fun buyFeed()

    fun commentEditTextChanged(char: CharSequence)

    fun sendComment(content: String)
}