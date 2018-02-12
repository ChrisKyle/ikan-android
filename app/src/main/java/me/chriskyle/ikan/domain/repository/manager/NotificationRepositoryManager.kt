package me.chriskyle.ikan.domain.repository.manager

import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.NotificationDataRepository
import me.chriskyle.ikan.data.repository.datastore.params.provider.NotificationParamProvider
import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
class NotificationRepositoryManager @Inject constructor(threadExecutor: ThreadExecutor,
                                                        postExecutionThread: PostExecutionThread,
                                                        private var notificationRepository: NotificationDataRepository) : BaseRepositoryManager(threadExecutor, postExecutionThread) {

    fun executeGetNotifications(notificationParamProvider: NotificationParamProvider,
                                subscriber: Observer<PagerEntity<List<NotificationEntity>>>) = notificationRepository.getNotifications(notificationParamProvider)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
            .subscribe(subscriber)

    fun executeDeleteNotification(notificationParamProvider: NotificationParamProvider, subscriber: Observer<NotificationEntity>) =
            notificationRepository.deleteNotification(notificationParamProvider)
                    .subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.scheduler)
                    .subscribe(subscriber)
}

