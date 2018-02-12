package me.chriskyle.ikan.presentation

import android.app.Activity
import android.content.Context
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.about.AboutActivity
import me.chriskyle.ikan.presentation.module.account.bind.AccountBindActivity
import me.chriskyle.ikan.presentation.module.account.edit.AccountEditActivity
import me.chriskyle.ikan.presentation.module.account.edit.AccountEditPresenterImpl.Companion.REQUEST_CODE_EDIT_NICKNAME
import me.chriskyle.ikan.presentation.module.account.edit.nickname.NicknameEditActivity
import me.chriskyle.ikan.presentation.module.assets.AssetsActivity
import me.chriskyle.ikan.presentation.module.base.web.simple.SimpleWebActivity
import me.chriskyle.ikan.presentation.module.feed.detail.FeedDetailActivity
import me.chriskyle.ikan.presentation.module.feed.download.DownloadActivity
import me.chriskyle.ikan.presentation.module.feed.trending.more.TrendingMoreActivity
import me.chriskyle.ikan.presentation.module.feed.upload.UploadActivity
import me.chriskyle.ikan.presentation.module.feed.watchhistory.WatchHistoryActivity
import me.chriskyle.ikan.presentation.module.help.HelpActivity
import me.chriskyle.ikan.presentation.module.help.feedback.FeedbackActivity
import me.chriskyle.ikan.presentation.module.notification.NotificationActivity
import me.chriskyle.ikan.presentation.module.setting.SettingActivity
import me.chriskyle.ikan.presentation.module.video.VideoPlayerActivity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/26.
 */
object Router {

    fun routeToFeedDetail(context: Context?, feed: FeedEntity) {
        context?.startActivity(FeedDetailActivity.getCallingIntent(context, feed))
    }

    fun routeToTrendingMore(context: Context?, category: String) {
        context?.startActivity(TrendingMoreActivity.getCallingIntent(context, category))
    }

    fun routeToNotification(context: Context?) {
        context?.startActivity(NotificationActivity.getCallingIntent(context))
    }

    fun routeToSetting(context: Context?) {
        context?.startActivity(SettingActivity.getCallingIntent(context))
    }

    fun routeToDownload(context: Context?) {
        context?.startActivity(DownloadActivity.getCallingIntent(context))
    }

    fun routeToUpload(context: Context?) {
        context?.startActivity(UploadActivity.getCallingIntent(context))
    }

    fun routeToWatchHistory(context: Context?) {
        context?.startActivity(WatchHistoryActivity.getCallingIntent(context))
    }

    fun routeToAccountBind(context: Context?) {
        context?.startActivity(AccountBindActivity.getCallingIntent(context))
    }

    fun routeToSimpleWeb(context: Context?, url: String, title: String) {
        context?.startActivity(SimpleWebActivity.getCallingIntent(context, url, title))
    }

    fun routeToAbout(context: Context?) {
        context?.startActivity(AboutActivity.getCallingIntent(context))
    }

    fun routeToHelp(context: Context?) {
        context?.startActivity(HelpActivity.getCallingIntent(context))
    }

    fun routeToFeedback(context: Context?) {
        context?.startActivity(FeedbackActivity.getCallingIntent(context))
    }

    fun routeToAccountAssets(context: Context?) {
        context?.startActivity(AssetsActivity.getCallingIntent(context))
    }

    fun routeToVideoPlayer(context: Context?, feed: FeedEntity) {
        context?.startActivity(VideoPlayerActivity.getCallingIntent(context, feed))
    }

    fun routeToAccountEdit(context: Context?) {
        context?.startActivity(AccountEditActivity.getCallingIntent(context))
    }

    fun routerToNicknameEdit(context: Context?) {
        (context as Activity).startActivityForResult(NicknameEditActivity.getCallingIntent(context),
                REQUEST_CODE_EDIT_NICKNAME)
    }
}
