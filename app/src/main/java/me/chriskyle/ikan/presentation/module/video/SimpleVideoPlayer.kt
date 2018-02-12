package me.chriskyle.ikan.presentation.module.video

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet

import com.shuyu.gsyvideoplayer.model.GSYVideoModel
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class SimpleVideoPlayer : StandardGSYVideoPlayer {

    var onVideoPlayListener: OnVideoPlayListener? = null

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag!!)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setup(gsyVideoModel: GSYVideoModel, cacheWithPlay: Boolean): Boolean {
        val set = setUp(gsyVideoModel.url, cacheWithPlay, gsyVideoModel.title)
        if (!TextUtils.isEmpty(gsyVideoModel.title)) {
            mTitleTextView.text = gsyVideoModel.title
        }
        return set
    }

    override fun prepareVideo() {
        onVideoPlayListener?.onPreparePlay()
    }

    fun start() {
        startPrepare()
    }

    override fun onPrepared() {
        super.onPrepared()

        addTextureView()
    }

    interface OnVideoPlayListener {

        fun onPreparePlay()
    }
}