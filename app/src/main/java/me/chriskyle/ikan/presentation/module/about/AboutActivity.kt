package me.chriskyle.ikan.presentation.module.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.BindView
import butterknife.OnClick
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import me.chriskyle.ikan.presentation.module.share.SharePopWindow
import me.chriskyle.library.design.table.TableRow
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.internal.util.BitmapUtils
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.ShareWebMedia
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AboutActivity : BaseActivity<AboutView, AboutPresenter>(), AboutView, ShareListener {

    @BindView(R.id.current_version)
    @JvmField
    var currentVersion: TableRow? = null

    @BindView(R.id.version_update)
    @JvmField
    var versionUpdate: TableRow? = null

    @Inject
    override lateinit var presenter: AboutPresenter

    private var shareView: SharePopWindow? = null

    override val layout: Int
        get() = R.layout.activity_about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.checkCurrentVersion()
        presenter.checkVersionUpdate()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().aboutComponent(AboutModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.setting_about)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        shareView?.onActivityResult(requestCode, resultCode, data)
    }

    override fun renderVersion(version: String) {
        currentVersion?.setSecondaryTitle(version)
    }

    override fun showSharePopupWindow() {
        val shareMedia = ShareWebMedia()
        shareMedia.title = "分享网页测试"
        shareMedia.description = "分享网页测试"
        shareMedia.webPageUrl = "http://www.baidu.com"
        shareMedia.thumb = BitmapUtils.resToBitmap(applicationContext, R.mipmap.ic_launcher)

        shareView = SharePopWindow(this, shareMedia, this)
        shareView!!.showMoreWindow(currentVersion)
    }

    @OnClick(R.id.version_update)
    fun versionUpdate() {
        presenter.versionUpdate()
    }

    @OnClick(R.id.share)
    fun share() {
        presenter.share()
    }

    @OnClick(R.id.feedback)
    fun feedback() {
        presenter.feedback()
    }

    override fun onShareComplete(platformType: PlatformType) {
        showMessage(getString(R.string.share_success))
    }

    override fun onShareError(platformType: PlatformType, errMsg: String) {
        showMessage(errMsg)
    }

    override fun onShareCancel(platformType: PlatformType) {
    }

    override fun onDestroy() {
        shareView?.release()

        super.onDestroy()
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, AboutActivity::class.java)
    }
}