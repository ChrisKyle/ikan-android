package me.chriskyle.ikan.presentation.module.video

import android.content.res.Configuration
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import me.chriskyle.ikan.presentation.module.base.lce.LceActivity
import me.chriskyle.library.mvp.lce.MvpLcePresenter
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/24.
 */
abstract class BaseVideoDetailActivity<V : MvpLceView, P : MvpLcePresenter<V>> : LceActivity<V, P>(),
        StandardVideoAllCallBack {

    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    protected var orientationUtils: OrientationUtils? = null

    abstract val videoOptionBuilder: GSYVideoOptionBuilder?
    abstract val detailOrientationRotateAuto: Boolean

    open fun initVideo() {
        orientationUtils = OrientationUtils(this, getVideoPlayer())
        orientationUtils!!.isEnable = false
        if (getVideoPlayer()?.fullscreenButton != null) {
            getVideoPlayer()?.fullscreenButton?.setOnClickListener {
                orientationUtils!!.resolveByClick()
                getVideoPlayer()?.startWindowFullscreen(this@BaseVideoDetailActivity, true, true)

                clickForFullScreen()
            }
        }
    }

    fun initVideoBuilderMode() {
        initVideo()
        videoOptionBuilder?.setVideoAllCallBack(this)?.build(getVideoPlayer())
    }

    open fun getVideoPlayer(): GSYBaseVideoPlayer? = null

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils!!.backToProtVideo()
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }

        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()

        getVideoPlayer()?.currentPlayer?.onVideoPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()

        getVideoPlayer()?.currentPlayer?.onVideoResume()
        isPause = false
    }

    override fun onDestroy() {
        if (isPlay) {
            getVideoPlayer()?.currentPlayer?.release()
        }

        orientationUtils?.releaseListener()

        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (isPlay && !isPause) {
            getVideoPlayer()?.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    override fun onPrepared(url: String, vararg objects: Any) {
        if (orientationUtils == null) {
            throw NullPointerException("initVideo() or initVideoBuilderMode() first")
        }

        orientationUtils?.isEnable = detailOrientationRotateAuto
        isPlay = true
    }

    override fun onClickStartIcon(url: String, vararg objects: Any) {}

    override fun onClickStartError(url: String, vararg objects: Any) {}

    override fun onClickStop(url: String, vararg objects: Any) {}

    override fun onClickStopFullscreen(url: String, vararg objects: Any) {}

    override fun onClickResume(url: String, vararg objects: Any) {}

    override fun onClickResumeFullscreen(url: String, vararg objects: Any) {}

    override fun onClickSeekbar(url: String, vararg objects: Any) {}

    override fun onClickSeekbarFullscreen(url: String, vararg objects: Any) {}

    override fun onAutoComplete(url: String, vararg objects: Any) {}

    override fun onEnterFullscreen(url: String, vararg objects: Any) {}

    override fun onQuitFullscreen(url: String, vararg objects: Any) {
        orientationUtils?.backToProtVideo()
    }

    override fun onQuitSmallWidget(url: String, vararg objects: Any) {}

    override fun onEnterSmallWidget(url: String, vararg objects: Any) {}

    override fun onTouchScreenSeekVolume(url: String, vararg objects: Any) {}

    override fun onTouchScreenSeekPosition(url: String, vararg objects: Any) {}

    override fun onTouchScreenSeekLight(url: String, vararg objects: Any) {}

    override fun onPlayError(url: String, vararg objects: Any) {}

    override fun onClickStartThumb(url: String, vararg objects: Any) {}

    override fun onClickBlank(url: String, vararg objects: Any) {}

    override fun onClickBlankFullscreen(url: String, vararg objects: Any) {}

    open fun clickForFullScreen() {}
}
