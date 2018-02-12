package me.chriskyle.ikan.presentation.module.feed.detail

import android.content.Intent
import android.text.TextUtils
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadLargeFileListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.model.FileDownloadStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.CommentEntity
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.datastore.local.db.DbHelper
import me.chriskyle.ikan.data.repository.datastore.params.provider.FeedParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.event.LoginEvent
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.ikan.presentation.module.feed.download.DownloadEntity
import me.chriskyle.ikan.presentation.module.feed.download.DownloadManager
import me.chriskyle.ikan.presentation.module.feed.download.OnDownloadStatusChangedListener
import me.chriskyle.ikan.presentation.module.feed.download.Util
import me.chriskyle.library.rxcupboard.RxCupboard
import me.chriskyle.library.rxcupboard.RxDatabase
import me.chriskyle.library.eventbus.Bus
import java.lang.ref.WeakReference

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class FeedDetailPresenterImpl(private val bus: Bus,
                              private val accountRepositoryManager: AccountRepositoryManager,
                              private val feedRepositoryManager: FeedRepositoryManager)
    : BasePresenter<FeedDetailView>(), FeedDetailPresenter, OnDownloadStatusChangedListener {

    private lateinit var cupboard: RxDatabase
    private lateinit var feedEntity: FeedEntity

    private var comments: MutableList<CommentEntity> = mutableListOf()
    private var currentPage = 1

    override fun onViewAttached() {
        super.onViewAttached()
        bus.subscribe(this)
    }

    override fun onViewDeAttached() {
        bus.unSubscribe(this)
        super.onViewDeAttached()
    }

    override fun dispatchIntent(intent: Intent) {
        feedEntity = intent.getParcelableExtra(FeedDetailActivity.INTENT_EXTRA_KEY_FEED)
        view?.renderFeedBaseInfo(feedEntity)

        loadNewestFeedDetail()
    }

    override fun checkLoginStatus() {
        if (isLogged()) {
            view?.renderLoggedStatus()
        } else {
            view?.renderUnLoginStatus()
        }
    }

    override fun initDownloader() {
        cupboard = RxCupboard.withDefault(DbHelper.getConnection(view?.ctx))
        DownloadManager.getImpl(view?.ctx!!)!!.init()
        DownloadManager.getImpl(view?.ctx!!)!!.bindDownloadStatusChangedListener(WeakReference(this))
    }

    private fun loadNewestFeedDetail() {
        val paramProvider = FeedParamProvider()
        paramProvider.feedId(feedEntity.id)
        feedRepositoryManager.executeGetFeedDetail(paramProvider, LoadFeedDetailObserver())
    }

    private fun loadFeedComments() {
        view?.showLoadingView()

        currentPage = 1
        val paramProvider = FeedParamProvider()
        paramProvider.feedId(feedEntity.id)
        paramProvider.page(currentPage)
        feedRepositoryManager.executeGetFeedComments(paramProvider, LoadFeedCommentObserver())
    }

    override fun refresh() {
        currentPage = 1
        val paramProvider = FeedParamProvider()
        paramProvider.feedId(feedEntity.id)
        paramProvider.page(currentPage)
        feedRepositoryManager.executeGetFeedComments(paramProvider, RefreshFeedCommentObserver())
    }

    override fun loadmore() {
        val paramProvider = FeedParamProvider()
        paramProvider.feedId(feedEntity.id)
        paramProvider.page(++currentPage)
        feedRepositoryManager.executeGetFeedComments(paramProvider, LoadMoreFeedCommentObserver())
    }

    override fun reload() {
        loadFeedComments()
    }

    override fun like() {
        if (!isLogged()) {
            view?.showLoginFragment()
            return
        } else {
            val feedParamProvider = FeedParamProvider()
            feedParamProvider.feedId(feedEntity.id)
            feedRepositoryManager.executeLikeFeed(feedParamProvider, LikeFeedObserver())
        }
    }

    override fun dislike() {
        val feedParamProvider = FeedParamProvider()
        feedParamProvider.feedId(feedEntity.id)

        feedRepositoryManager.executeDislikeFeed(feedParamProvider, DislikeFeedObserver())
    }

    override fun download() {
        if (!isLogged()) {
            view?.showLoginFragment()
        } else {
            if (feedEntity.diamond == 0 || feedEntity.isBought) {
                feedEntity.let {
                    view?.showStartDownload()

                    DownloadManager.getImpl(view?.ctx!!)?.addTask(feedEntity.title, feedEntity.url, feedEntity.thumbnail)
                    FileDownloader.getImpl()
                            .create(feedEntity.url)
                            .setPath(Util.getSaveFilePath(feedEntity.url, feedEntity.fileName))
                            .setCallbackProgressTimes(100)
                            .setListener(downloadListener)
                            .start()
                }
            } else {
                view?.showBuyFeedDialog(String.format(view?.ctx!!.getString(R.string.feed_detail_buy_tip_msg_for_download), feedEntity.diamond))
            }
        }
    }

    override fun share() {
        view?.showSharePopupWindow()
    }

    override fun preparePlay() {
        if (isLogged()) {
            if (feedEntity.diamond == 0 || feedEntity.isBought) {
                view?.showVideoPlay()
            } else {
                view?.showBuyFeedDialog(String.format(view?.ctx!!.getString(R.string.feed_detail_buy_tip_msg_for_play), feedEntity.diamond))
            }
        } else {
            view?.showLoginFragment()
        }
    }

    override fun buyFeed() {
        val feedParamProvider = FeedParamProvider()
        feedParamProvider.feedId(feedEntity.id)
        feedRepositoryManager.executeBuyFeed(feedParamProvider, BuyFeedObserver())
    }

    override fun commentEditTextChanged(char: CharSequence) {
        if (TextUtils.isEmpty(char)) {
            view?.disableSendComment()
        } else {
            view?.enableSendComment()
        }
    }

    override fun sendComment(content: String) {
        val feedParamProvider = FeedParamProvider()
        feedParamProvider.feedId(feedEntity.id)
        feedParamProvider.commentContent(content)
        feedRepositoryManager.executeAddFeedComment(feedParamProvider, SendCommentObserver())
    }

    override fun onDownloadStatusChanged() {
    }

    private fun loadDownloadedFeeds() {
        addSubscription(cupboard.query(DownloadEntity::class.java)
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { downloadModels ->
                    DownloadManager.getImpl(view?.ctx!!)?.addDownloadItems(downloadModels)
                })
    }

    private val downloadListener = object : FileDownloadLargeFileListener() {

        override fun started(task: BaseDownloadTask?) {
            view?.renderDownloadStatus(view?.ctx?.getString(R.string.tasks_manager_status_started)!!)
        }

        override fun connected(task: BaseDownloadTask?, etag: String?, isContinue: Boolean, soFarBytes: Long, totalBytes: Long) {
            view?.renderDownloadStatus(view?.ctx?.getString(R.string.tasks_manager_status_connected)!!)
        }

        override fun error(task: BaseDownloadTask, e: Throwable) {
            view?.renderDownloadError()
        }

        override fun pending(task: BaseDownloadTask, soFarBytes: Long, totalBytes: Long) {
            view?.renderDownloadStatus(view?.ctx?.getString(R.string.tasks_manager_status_pending)!!)
        }

        override fun progress(task: BaseDownloadTask, soFarBytes: Long, totalBytes: Long) {
            view?.updateDownloading(FileDownloadStatus.progress.toInt())
        }

        override fun paused(task: BaseDownloadTask, soFarBytes: Long, totalBytes: Long) {
            view?.renderDownloadStatus(view?.ctx?.getString(R.string.tasks_manager_status_paused)!!)
        }

        override fun warn(task: BaseDownloadTask) {}

        override fun completed(task: BaseDownloadTask) {
            view?.updateDownloaded()
        }
    }

    private fun isLogged() = accountRepositoryManager.executeGetLogged() != null

    fun onLoginEvent(event: LoginEvent) {
        checkLoginStatus()
    }

    private fun loadFeedDetailSuccess(feedEntity: FeedEntity) {
        loadFeedComments()

        this.feedEntity = feedEntity
        view?.renderFeedBaseInfo(feedEntity)
    }

    private fun loadFeedDetailError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showStatusMsg(msg)
    }

    private fun likeFeedSuccess() {
        feedEntity.likeCount = feedEntity.likeCount + 1
        view?.renderLikeCount(feedEntity.likeCount)
    }

    private fun likeFeedError(e: Throwable) {}

    private fun dislikeFeedSuccess() {
        feedEntity.likeCount = feedEntity.likeCount - 1
        view?.renderLikeCount(feedEntity.likeCount)
    }

    private fun dislikeFeedError(e: Throwable) {}

    private fun sendCommentSuccess(commentEntity: CommentEntity) {
        view?.showSendCommentSuccess()

        commentEntity.account = accountRepositoryManager.executeGetLogged()!!
        comments.add(0, commentEntity)
        view?.showContent()
        view?.renderComments(comments)
    }

    private fun sendCommentError(e: Throwable) {}

    private fun loadFeedCommentSuccess(pagerComment: PagerEntity<List<CommentEntity>>) {
        if (pagerComment.list!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            comments = pagerComment.list as MutableList<CommentEntity>

            view?.showContent()
            view?.renderComments(comments)

            if (pagerComment.total == comments.size) {
                view?.showNoMoreData()
            }
        }
    }

    private fun loadFeedCommentError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showErrorView(null)
        view?.showStatusMsg(msg)
    }

    private fun refreshFeedCommentSuccess(pagerComment: PagerEntity<List<CommentEntity>>) {
        view?.showRefreshCompleted()

        loadFeedCommentSuccess(pagerComment)
    }

    private fun refreshFeedCommentError(e: Throwable) {
        view?.let {
            view?.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private fun loadMoreFeedCommentSuccess(pagerComment: PagerEntity<List<CommentEntity>>) {
        view?.let {
            view!!.showLoadMoreCompleted()
            view!!.showContent()

            if (pagerComment.list!!.isEmpty()) {
                view!!.showEmptyView()

            } else {
                comments.addAll(pagerComment.list!!)
                view!!.renderComments(comments)
            }

            if (pagerComment.total == comments.size) {
                view!!.showNoMoreData()
            }
        }
    }

    private fun loadMoreFeedCommentError(e: Throwable) {
        view?.let {
            view!!.showLoadMoreCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private fun buyFeedSuccess() {
        feedEntity.isBought = true
        view?.showBuyFeedSuccess()
    }

    private fun buyFeedError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view!!.showStatusMsg(msg)
    }

    inner class LoadFeedDetailObserver : DefaultObserver<FeedEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onError(e: Throwable) {
            loadFeedDetailError(e)
        }

        override fun onNext(t: FeedEntity) {
            loadFeedDetailSuccess(t)
        }
    }

    inner class LikeFeedObserver : DefaultObserver<FeedEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onError(e: Throwable) {
            likeFeedError(e)
        }

        override fun onComplete() {
            likeFeedSuccess()
        }
    }

    inner class DislikeFeedObserver : DefaultObserver<FeedEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onError(e: Throwable) {
            dislikeFeedError(e)
        }

        override fun onComplete() {
            dislikeFeedSuccess()
        }
    }

    inner class SendCommentObserver : DefaultObserver<CommentEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: CommentEntity) {
            sendCommentSuccess(t)
        }

        override fun onError(e: Throwable) {
            sendCommentError(e)
        }
    }

    inner class LoadFeedCommentObserver : DefaultObserver<PagerEntity<List<CommentEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<CommentEntity>>) {
            loadFeedCommentSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadFeedCommentError(e)
        }
    }

    inner class RefreshFeedCommentObserver : DefaultObserver<PagerEntity<List<CommentEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<CommentEntity>>) {
            refreshFeedCommentSuccess(t)
        }

        override fun onError(e: Throwable) {
            refreshFeedCommentError(e)
        }
    }

    inner class LoadMoreFeedCommentObserver : DefaultObserver<PagerEntity<List<CommentEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: PagerEntity<List<CommentEntity>>) {
            loadMoreFeedCommentSuccess(t)
        }

        override fun onError(e: Throwable) {
            loadMoreFeedCommentError(e)
        }
    }

    inner class BuyFeedObserver : DefaultObserver<Any>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onError(e: Throwable) {
            buyFeedError(e)
        }

        override fun onComplete() {
            buyFeedSuccess()
        }
    }
}