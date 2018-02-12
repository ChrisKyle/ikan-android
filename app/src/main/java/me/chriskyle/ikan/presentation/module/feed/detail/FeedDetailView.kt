package me.chriskyle.ikan.presentation.module.feed.detail

import me.chriskyle.ikan.data.entity.CommentEntity
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface FeedDetailView : MvpLceView {

    fun renderFeedBaseInfo(feed: FeedEntity)

    fun renderComments(comments: MutableList<CommentEntity>)

    fun renderDownloadStatus(status: String)

    fun renderDownloadError()

    fun updateDownloading(progress: Int)

    fun updateDownloaded()

    fun updateNotDownloaded()

    fun showStartDownload()

    fun showSharePopupWindow()

    fun showLoginFragment()

    fun renderUnLoginStatus()

    fun renderLoggedStatus()

    fun renderLikeCount(count: Long)

    fun enableSendComment()

    fun disableSendComment()

    fun showSendCommentSuccess()

    fun showVideoPlay()

    fun showBuyFeedDialog(msg: String)

    fun showBuyFeedSuccess()
}