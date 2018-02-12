package me.chriskyle.ikan.presentation.di.component

import dagger.Subcomponent
import me.chriskyle.ikan.presentation.di.PerActivity
import me.chriskyle.ikan.presentation.di.module.ActivityModule
import me.chriskyle.ikan.presentation.module.about.AboutComponent
import me.chriskyle.ikan.presentation.module.about.AboutModule
import me.chriskyle.ikan.presentation.module.account.bind.AccountBindComponent
import me.chriskyle.ikan.presentation.module.account.bind.AccountBindModule
import me.chriskyle.ikan.presentation.module.account.edit.AccountEditComponent
import me.chriskyle.ikan.presentation.module.account.edit.AccountEditModule
import me.chriskyle.ikan.presentation.module.account.edit.nickname.NicknameEditComponent
import me.chriskyle.ikan.presentation.module.account.edit.nickname.NicknameEditModule
import me.chriskyle.ikan.presentation.module.assets.AssetsComponent
import me.chriskyle.ikan.presentation.module.assets.AssetsModule
import me.chriskyle.ikan.presentation.module.base.web.simple.SimpleWebComponent
import me.chriskyle.ikan.presentation.module.base.web.simple.SimpleWebModule
import me.chriskyle.ikan.presentation.module.feed.detail.FeedDetailComponent
import me.chriskyle.ikan.presentation.module.feed.detail.FeedDetailModule
import me.chriskyle.ikan.presentation.module.feed.download.DownloadComponent
import me.chriskyle.ikan.presentation.module.feed.download.DownloadModule
import me.chriskyle.ikan.presentation.module.feed.trending.more.TrendingMoreComponent
import me.chriskyle.ikan.presentation.module.feed.trending.more.TrendingMoreModule
import me.chriskyle.ikan.presentation.module.feed.upload.UploadComponent
import me.chriskyle.ikan.presentation.module.feed.upload.UploadModule
import me.chriskyle.ikan.presentation.module.feed.watchhistory.WatchHistoryComponent
import me.chriskyle.ikan.presentation.module.feed.watchhistory.WatchHistoryModule
import me.chriskyle.ikan.presentation.module.help.HelpComponent
import me.chriskyle.ikan.presentation.module.help.HelpModule
import me.chriskyle.ikan.presentation.module.help.feedback.FeedbackComponent
import me.chriskyle.ikan.presentation.module.help.feedback.FeedbackModule
import me.chriskyle.ikan.presentation.module.main.MainComponent
import me.chriskyle.ikan.presentation.module.main.MainModule
import me.chriskyle.ikan.presentation.module.notification.NotificationComponent
import me.chriskyle.ikan.presentation.module.notification.NotificationModule
import me.chriskyle.ikan.presentation.module.setting.SettingComponent
import me.chriskyle.ikan.presentation.module.setting.SettingModule
import me.chriskyle.ikan.presentation.module.video.VideoPlayerComponent
import me.chriskyle.ikan.presentation.module.video.VideoPlayerModule

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun mainComponent(mainModule: MainModule): MainComponent

    fun trendingMoreComponent(trendingMoreModule: TrendingMoreModule): TrendingMoreComponent

    fun feedDetailComponent(feedDetailModule: FeedDetailModule): FeedDetailComponent

    fun settingComponent(settingModule: SettingModule): SettingComponent

    fun notificationComponent(notificationModule: NotificationModule): NotificationComponent

    fun helpComponent(helpModule: HelpModule): HelpComponent

    fun feedbackComponent(feedbackModule: FeedbackModule): FeedbackComponent

    fun downloadComponent(downloadModule: DownloadModule): DownloadComponent

    fun uploadComponent(uploadModule: UploadModule): UploadComponent

    fun watchHistoryComponent(watchHistoryModule: WatchHistoryModule): WatchHistoryComponent

    fun simpleWebComponent(simpleWebModule: SimpleWebModule): SimpleWebComponent

    fun aboutComponent(aboutModule: AboutModule): AboutComponent

    fun assetsComponent(assetsModule: AssetsModule): AssetsComponent

    fun accountBindComponent(accountBindModule: AccountBindModule): AccountBindComponent

    fun videoPlayerComponent(videoPlayerModule: VideoPlayerModule): VideoPlayerComponent

    fun accountEditComponent(accountEditModule: AccountEditModule): AccountEditComponent

    fun nicknameEditComponent(nicknameEditModule: NicknameEditModule): NicknameEditComponent
}