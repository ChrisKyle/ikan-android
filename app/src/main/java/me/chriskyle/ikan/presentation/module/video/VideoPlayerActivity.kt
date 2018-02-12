package me.chriskyle.ikan.presentation.module.video

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import butterknife.BindView
import com.facebook.drawee.view.SimpleDraweeView
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.model.GSYVideoModel
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class VideoPlayerActivity : BaseVideoDetailActivity<VideoPlayerView, VideoPlayerPresenter>(),
        VideoPlayerView, SimpleVideoPlayer.OnVideoPlayListener {

    @BindView(R.id.toolbar)
    @JvmField
    var toolbar: Toolbar? = null

    @BindView(R.id.detail_player)
    @JvmField
    var detailPlayer: SimpleVideoPlayer? = null

    private lateinit var thumbImageView: SimpleDraweeView

    @Inject
    override lateinit var presenter: VideoPlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.dispatchIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        presenter.dispatchIntent(intent)
    }

    override val layout: Int
        get() = R.layout.activity_video_player

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().videoPlayerComponent(VideoPlayerModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        toolbar?.setNavigationOnClickListener { onBackPressed() }

        thumbImageView = LayoutInflater.from(this as Activity).inflate(R.layout.layout_feed_thumb,
                null, false) as SimpleDraweeView

        detailPlayer?.setIsTouchWiget(true)
        detailPlayer?.titleTextView?.visibility = View.GONE
        detailPlayer?.backButton?.visibility = View.GONE
        detailPlayer?.isRotateViewAuto = false
        detailPlayer?.isLockLand = true
        detailPlayer?.isShowFullAnimation = false
        detailPlayer?.isNeedLockFull = true
        detailPlayer?.setStandardVideoAllCallBack(this)
        detailPlayer?.setLockClickListener { _, lock ->
            orientationUtils?.isEnable = !lock
        }

        detailPlayer?.onVideoPlayListener = this

        initVideo()
    }

    override fun onPreparePlay() {
        detailPlayer?.start()
    }

    override fun renderDownloadFeed(feed: FeedEntity) {
        feed.let {
            detailPlayer?.setup(GSYVideoModel(feed.url, feed.title), true)
            thumbImageView.setImageURI(feed.thumbnail)
            detailPlayer?.thumbImageView = thumbImageView
        }
    }

    override fun getVideoPlayer(): GSYBaseVideoPlayer? = detailPlayer

    override val videoOptionBuilder: GSYVideoOptionBuilder? = null

    override val detailOrientationRotateAuto = true

    companion object {

        const val INTENT_EXTRA_KEY_FEED: String = "feed"

        fun getCallingIntent(context: Context, feed: FeedEntity): Intent {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(INTENT_EXTRA_KEY_FEED, feed)
            return intent
        }
    }
}