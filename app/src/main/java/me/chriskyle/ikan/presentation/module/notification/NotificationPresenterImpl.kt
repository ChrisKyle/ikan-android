package me.chriskyle.ikan.presentation.module.notification

import android.text.TextUtils
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.NotificationParamProvider
import me.chriskyle.ikan.domain.exception.DefaultErrorBundle
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.NotificationRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.exception.ErrorMessageFactory
import me.chriskyle.ikan.presentation.module.base.BasePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class NotificationPresenterImpl(private val notificationRepositoryManager: NotificationRepositoryManager) :
        BasePresenter<NotificationView>(), NotificationPresenter {

    private lateinit var notificationEntities: MutableList<NotificationEntity>
    private var currentPage: Int = 1

    override fun loadNotifications() {
        view?.showLoadingView()

        currentPage = 1
        val notificationParamProvider = NotificationParamProvider()
        notificationParamProvider.page(currentPage)
        notificationRepositoryManager.executeGetNotifications(notificationParamProvider, LoadNotificationObserver())
    }

    override fun notificationDetail(notification: NotificationEntity) {
        if (!TextUtils.isEmpty(notification.url)) {
            Router.routeToSimpleWeb(view?.ctx, notification.url!!, view?.ctx!!.getString(R.string.notification_detail))
        }
    }

    override fun deleteNotification(notification: NotificationEntity) {
        notificationEntities.remove(notification)
        view?.renderNotifications(notificationEntities)

        notificationRepositoryManager.executeDeleteNotification(NotificationParamProvider().notificationId(notification.id),
                DeleteNotificationObserver())
    }

    override fun refresh() {
        currentPage = 1
        val notificationParamProvider = NotificationParamProvider()
        notificationParamProvider.page(currentPage)

        notificationRepositoryManager.executeGetNotifications(NotificationParamProvider(), RefreshNotificationObserver())
    }

    override fun loadmore() {
        val paramProvider = NotificationParamProvider()
        paramProvider.page(++currentPage)
        notificationRepositoryManager.executeGetNotifications(paramProvider, LoadmoreNotificationObserver())
    }

    override fun reload() {
        loadNotifications()
    }

    private fun loadNotificationSuccess(pagerEntity: PagerEntity<List<NotificationEntity>>) {
        if (pagerEntity.list == null || pagerEntity.list!!.isEmpty()) {
            view?.showEmptyView()
        } else {
            notificationEntities = pagerEntity.list as MutableList<NotificationEntity>

            view?.showContent()
            view?.renderNotifications(notificationEntities)

            if (pagerEntity.total == notificationEntities.size) {
                view?.showNoMoreData()
            }
        }
    }

    private fun loadNotificationError(e: Throwable) {
        view?.let {
            view?.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private fun refreshNotificationSuccess(pagerEntity: PagerEntity<List<NotificationEntity>>) {
        view?.showRefreshCompleted()

        loadNotificationSuccess(pagerEntity)
    }

    private fun refreshNotificationError(e: Throwable) {
        view?.let {
            view?.showRefreshCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showStatusMsg(msg)
        }
    }

    private fun loadmoreNotificationSuccess(pagerEntity: PagerEntity<List<NotificationEntity>>) {
        view?.let {
            view!!.showLoadMoreCompleted()
            view!!.showContent()

            if (pagerEntity.list!!.isEmpty()) {
                view!!.showEmptyView()

            } else {
                notificationEntities.addAll(pagerEntity.list!!)
                view!!.renderNotifications(notificationEntities)
            }

            if (pagerEntity.total == notificationEntities.size) {
                view!!.showNoMoreData()
            }
        }
    }

    private fun loadmoreNotificationError(e: Throwable) {
        view?.let {
            view!!.showLoadMoreCompleted()
            val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
            view!!.showErrorView(null)
            view!!.showStatusMsg(msg)
        }
    }

    private fun deleteNotificationSuccess(notification: NotificationEntity) {
        view?.showStatusMsg(view?.ctx!!.getString(R.string.notification_delete_success))
    }

    private fun deleteNotificationError(e: Throwable) {
        val msg = ErrorMessageFactory.create(view?.ctx!!, DefaultErrorBundle(e as Exception).exception)
        view?.showStatusMsg(msg)
    }

    private inner class LoadNotificationObserver : DefaultObserver<PagerEntity<List<NotificationEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(pagerEntity: PagerEntity<List<NotificationEntity>>) {
            loadNotificationSuccess(pagerEntity)
        }

        override fun onError(e: Throwable) {
            loadNotificationError(e)
        }
    }

    private inner class RefreshNotificationObserver : DefaultObserver<PagerEntity<List<NotificationEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(pagerEntity: PagerEntity<List<NotificationEntity>>) {
            refreshNotificationSuccess(pagerEntity)
        }

        override fun onError(e: Throwable) {
            refreshNotificationError(e)
        }
    }

    private inner class LoadmoreNotificationObserver : DefaultObserver<PagerEntity<List<NotificationEntity>>>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(pagerEntity: PagerEntity<List<NotificationEntity>>) {
            loadmoreNotificationSuccess(pagerEntity)
        }

        override fun onError(e: Throwable) {
            loadmoreNotificationError(e)
        }
    }

    private inner class DeleteNotificationObserver : DefaultObserver<NotificationEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: NotificationEntity) {
            deleteNotificationSuccess(t)
        }

        override fun onError(e: Throwable) {
            deleteNotificationError(e)
        }
    }
}