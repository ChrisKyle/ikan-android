package me.chriskyle.ikan.presentation.module.feed.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import butterknife.OnTextChanged
import com.facebook.drawee.view.SimpleDraweeView
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.model.GSYVideoModel
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.CommentEntity
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.module.account.login.LoginFragment
import me.chriskyle.ikan.presentation.module.feed.FeedItemDecoration
import me.chriskyle.ikan.presentation.module.share.SharePopWindow
import me.chriskyle.ikan.presentation.module.video.BaseVideoDetailActivity
import me.chriskyle.ikan.presentation.module.video.SimpleVideoPlayer
import me.chriskyle.library.design.dialog.AlertDialogFragment
import me.chriskyle.library.design.fragmentanim.FragmentTransactionWrapper
import me.chriskyle.library.design.fragmentanim.PlaceHolderFragment
import me.chriskyle.library.design.viewpager.ParallaxTransformer
import me.chriskyle.library.social.SocialApi
import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.internal.util.BitmapUtils
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.ShareWebMedia
import me.chriskyle.library.support.likebutton.LikeButton
import me.chriskyle.library.support.likebutton.OnLikeListener
import me.chriskyle.library.support.slideup.SlideUp
import me.chriskyle.library.support.slideup.SlideUpBuilder
import me.chriskyle.library.toolkit.utils.DateUtils
import me.chriskyle.library.toolkit.utils.InputMethodUtils
import me.chriskyle.library.toolkit.utils.StatusBarUtil
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class FeedDetailActivity : BaseVideoDetailActivity<FeedDetailView, FeedDetailPresenter>(), FeedDetailView,
        ShareListener, SimpleVideoPlayer.OnVideoPlayListener {

    @BindView(R.id.toolbar)
    @JvmField
    var toolbar: Toolbar? = null

    @BindView(R.id.view_need_offset)
    @JvmField
    var viewNeedOffset: CoordinatorLayout? = null

    @BindView(R.id.video_name)
    @JvmField
    var videoName: TextView? = null

    @BindView(R.id.like)
    @JvmField
    var like: LikeButton? = null

    @BindView(R.id.share)
    @JvmField
    var share: LikeButton? = null

    @BindView(R.id.detail_player)
    @JvmField
    var videoPlayer: SimpleVideoPlayer? = null

    @BindView(R.id.synopsis_lay)
    @JvmField
    var introductionLay: View? = null

    @BindView(R.id.synopsis)
    @JvmField
    var synopsis: TextView? = null

    @BindView(R.id.synopsis_actors)
    @JvmField
    var synopsisActors: ViewPager? = null

    @BindView(R.id.introduction_video_name)
    @JvmField
    var synopsisVideoName: TextView? = null

    @BindView(R.id.content)
    @JvmField
    var content: View? = null

    @BindView(R.id.watch_count)
    @JvmField
    var watchCount: TextView? = null

    @BindView(R.id.like_count)
    @JvmField
    var likeCount: TextView? = null

    @BindView(R.id.account_avatar)
    @JvmField
    var accountAvatar: SimpleDraweeView? = null

    @BindView(R.id.account_nickname)
    @JvmField
    var nickname: TextView? = null

    @BindView(R.id.upload_date)
    @JvmField
    var uploadDate: TextView? = null

    @BindView(R.id.loading_view)
    @JvmField
    var loadingView: View? = null

    @BindView(R.id.comment_edit)
    @JvmField
    var commentEdit: EditText? = null

    @BindView(R.id.comment_send)
    @JvmField
    var commentSend: ImageView? = null

    @BindView(R.id.price)
    @JvmField
    var price: TextView? = null

    private var placeHolderFragment: PlaceHolderFragment? = null

    private lateinit var thumbImageView: SimpleDraweeView
    private lateinit var slideIntroduction: SlideUp
    private lateinit var commentAdapter: CommentAdapter

    @Inject
    override lateinit var presenter: FeedDetailPresenter

    private var shareView: SharePopWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.dispatchIntent(intent)
        presenter.initDownloader()
        presenter.checkLoginStatus()
    }

    override val layout: Int
        get() = R.layout.activity_feed_detail

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().feedDetailComponent(FeedDetailModule()).inject(this)
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        super.initView()

        StatusBarUtil.setTranslucentForImageView(this, 0, viewNeedOffset)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        toolbar?.setNavigationOnClickListener { onBackPressed() }

        slideIntroduction = SlideUpBuilder(introductionLay)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build()

        content?.viewTreeObserver?.addOnGlobalLayoutListener {
            val params: CoordinatorLayout.LayoutParams = CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            val height = videoPlayer?.height!!

            val fixHeight = height - toolbar?.height!! + this.resources.getDimension(R.dimen.feed_detail_content_fix_height).toInt()
            params.setMargins(0, fixHeight, 0, 0)
            content?.layoutParams = params
        }

        loadingView?.viewTreeObserver?.addOnGlobalLayoutListener {
            val params: FrameLayout.LayoutParams = loadingView?.layoutParams as FrameLayout.LayoutParams
            val fixHeight = this.resources.getDimension(R.dimen.feed_detail_lce_icon_fix_margin_top).toInt()
            params.setMargins(0, fixHeight, 0, 0)
            loadingView?.layoutParams = params
        }
        emptyLceView?.icon?.viewTreeObserver?.addOnGlobalLayoutListener {
            val params: LinearLayout.LayoutParams = emptyLceView?.icon?.layoutParams as LinearLayout.LayoutParams
            val fixHeight = this.resources.getDimension(R.dimen.feed_detail_lce_icon_fix_margin_top).toInt()
            params.setMargins(0, fixHeight, 0, 0)
            emptyLceView?.icon?.layoutParams = params
        }
        errorLceView?.icon?.viewTreeObserver?.addOnGlobalLayoutListener {
            val params: LinearLayout.LayoutParams = errorLceView?.icon?.layoutParams as LinearLayout.LayoutParams
            val fixHeight = this.resources.getDimension(R.dimen.feed_detail_lce_icon_fix_margin_top).toInt()
            params.setMargins(0, fixHeight, 0, 0)
            errorLceView?.icon?.layoutParams = params
        }

        refreshLayout?.isEnableRefresh = false
        refreshLayout!!.refreshHeader!!.setPrimaryColors(ContextCompat.getColor(this, R.color.white))

        emptyLceView?.setIcon(R.drawable.ic_comment_empty)
        emptyLceView?.setDesText(R.string.feed_comment_empty)

        thumbImageView = LayoutInflater.from(this as Activity).inflate(R.layout.layout_feed_thumb,
                null, false) as SimpleDraweeView

        like?.setOnLikeListener(object : OnLikeListener {

            override fun liked(likeButton: LikeButton?) {
                presenter.like()
            }

            override fun unLiked(likeButton: LikeButton?) {
                presenter.dislike()
            }
        })

        commentSend?.isEnabled = false

        synopsisActors?.setPageTransformer(false, ParallaxTransformer(this))

        videoPlayer?.setIsTouchWiget(true)
        videoPlayer?.titleTextView?.visibility = View.GONE
        videoPlayer?.backButton?.visibility = View.GONE
        videoPlayer?.isRotateViewAuto = false
        videoPlayer?.isLockLand = false
        videoPlayer?.isShowFullAnimation = false
        videoPlayer?.isNeedLockFull = true
        videoPlayer?.setStandardVideoAllCallBack(this)
        videoPlayer?.onVideoPlayListener = this
        videoPlayer?.setLockClickListener { _, lock ->
            orientationUtils?.isEnable = !lock
        }

        commentAdapter = CommentAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.addItemDecoration(FeedItemDecoration(2))
        recyclerView?.adapter = commentAdapter

        initPlaceHolderFragment()
        initVideo()
    }

    private fun initPlaceHolderFragment() {
        placeHolderFragment = PlaceHolderFragment()

        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.add(R.id.root_fragment_container, placeHolderFragment)
        fragmentTransaction.commit()
    }

    override fun showLoginFragment() {
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        val fragmentTransactionExtended = FragmentTransactionWrapper(this, fragmentTransaction,
                placeHolderFragment, LoginFragment(), R.id.root_fragment_container)
        fragmentTransactionExtended.addTransition(FragmentTransactionWrapper.SCALEXY)
        fragmentTransactionExtended.commit()
    }

    override fun renderUnLoginStatus() {
        like?.isEnabled = false
    }

    override fun renderLoggedStatus() {
        like?.isEnabled = true
    }

    override fun renderLikeCount(count: Long) {
        likeCount?.text = String.format(getString(R.string.feed_detail_like_count), count)
    }

    override fun enableSendComment() {
        commentSend?.isEnabled = true
    }

    override fun disableSendComment() {
        commentSend?.isEnabled = false
    }

    override fun showSendCommentSuccess() {
        showMessage(R.string.feed_comment_success)
        commentEdit?.text = null
    }

    override fun showVideoPlay() {
        videoPlayer?.start()
    }

    override fun showBuyFeedDialog(msg: String) {
        val fragment = AlertDialogFragment.Builder().id(1)
                .setTitle(getString(R.string.feed_detail_buy_tip_title))
                .setMessage(msg)
                .setPositive(R.string.feed_detail_buy_tip_buy)
                .setNegative(R.string.cancel)
                .build()
                .setOnDialogActionClickListener { _, view ->
                    if (view.id == R.id.action0) {
                        presenter.buyFeed()
                    }
                    true
                }
        fragment.setTargetFragment(null, 1)
        supportFragmentManager
                .beginTransaction()
                .add(fragment, null)
                .commit()
    }

    override fun showBuyFeedSuccess() {
        showMessage(R.string.feed_detail_buy_success)
    }

    override fun renderFeedBaseInfo(feed: FeedEntity) {
        feed.let {
            videoName?.text = String.format(getString(R.string.feed_detail_video_name), feed.title)
            videoPlayer?.setup(GSYVideoModel(feed.url, feed.title), true)
            thumbImageView.setImageURI(feed.thumbnail)
            videoPlayer?.thumbImageView = thumbImageView

            uploadDate?.text = String.format(getString(R.string.feed_detail_upload_date),
                    DateUtils.getTimeOral(feed.createTime))
            watchCount?.text = String.format(getString(R.string.feed_detail_watch_count), feed.watchCount)
            likeCount?.text = String.format(getString(R.string.feed_detail_like_count), feed.likeCount)

            like?.isLiked = feed.isLike

            feed.account.let {
                accountAvatar?.setImageURI(feed.account.avatarUrl)
                nickname?.text = feed.account.nickname
            }

            price?.text = String.format(getString(R.string.feed_detail_sell_price), feed.diamond)

            synopsisVideoName?.text = feed.title
            synopsis?.text = String.format(getString(R.string.feed_detail_video_synopsis), feed.synopsis)
            val adapter = ActorViewPagerAdapter(supportFragmentManager)
            synopsisActors?.adapter = adapter
            adapter.actors = feed.actors
            adapter.notifyDataSetChanged()
        }
    }

    override fun renderComments(comments: MutableList<CommentEntity>) {
        commentAdapter.commentEntities = comments
        commentAdapter.notifyDataSetChanged()
    }

    override fun renderDownloadStatus(status: String) {
    }

    override fun renderDownloadError() {
    }

    override fun updateDownloading(progress: Int) {
    }

    override fun updateDownloaded() {
    }

    override fun updateNotDownloaded() {
    }

    override fun showStartDownload() {
        showMessage(R.string.feed_start_download)
    }

    override fun showSharePopupWindow() {
        val shareMedia = ShareWebMedia()
        shareMedia.title = "分享网页测试"
        shareMedia.description = "分享网页测试"
        shareMedia.webPageUrl = "http://www.ikan.video"
        shareMedia.thumb = BitmapUtils.resToBitmap(applicationContext, R.mipmap.ic_launcher)

        shareView = SharePopWindow(this, shareMedia, this)
        shareView!!.showMoreWindow(share)
    }

    @OnClick(R.id.download)
    fun download() {
        presenter.download()
    }

    @OnClick(R.id.share)
    fun share() {
        presenter.share()
    }

    @OnClick(R.id.introduction)
    fun introduction() {
        slideIntroduction.show()
    }

    @OnClick(R.id.introduction_close)
    fun introductionClose() {
        slideIntroduction.hide()
    }

    @OnClick(R.id.comment_send)
    fun sendComment() {
        InputMethodUtils.hideSoftInput(commentEdit)

        presenter.sendComment(commentEdit?.text.toString())
    }

    @OnTextChanged(R.id.comment_edit, callback = OnTextChanged.Callback.TEXT_CHANGED)
    fun feedbackEditTextChanged(char: CharSequence) {
        presenter.commentEditTextChanged(char)
    }

    override fun onShareComplete(platformType: PlatformType) {
        showMessage(R.string.share_success)
    }

    override fun onShareError(platformType: PlatformType, errMsg: String) {
        showMessage(errMsg)
    }

    override fun onShareCancel(platformType: PlatformType) {
    }

    override fun onPreparePlay() {
        presenter.preparePlay()
    }

    override fun getVideoPlayer(): GSYBaseVideoPlayer? = videoPlayer

    override val videoOptionBuilder: GSYVideoOptionBuilder? = null

    override val detailOrientationRotateAuto = true

    override fun onBackPressed() {
        if (slideIntroduction.isVisible) {
            slideIntroduction.hide()
            return
        }

        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            shareView?.onActivityResult(requestCode, resultCode, data)
            SocialApi.instance.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        shareView?.release()

        super.onDestroy()
    }

    companion object {

        const val INTENT_EXTRA_KEY_FEED: String = "feed"

        fun getCallingIntent(context: Context, feed: FeedEntity): Intent {
            val intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_KEY_FEED, feed)
            return intent
        }
    }
}