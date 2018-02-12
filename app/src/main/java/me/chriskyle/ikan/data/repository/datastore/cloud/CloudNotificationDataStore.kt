package me.chriskyle.ikan.data.repository.datastore.cloud

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.NotificationEntity
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.repository.datastore.NotificationDataStore
import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor
import me.chriskyle.ikan.data.repository.datastore.cloud.request.service.NotificationService
import me.chriskyle.ikan.data.repository.datastore.cloud.response.ResponseFlatResult
import me.chriskyle.ikan.data.repository.datastore.params.provider.NotificationParamProvider
import me.chriskyle.library.net.connection.Connector

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
class CloudNotificationDataStore(connector: Connector, tokenInterceptor: TokenInterceptor)
    : BaseCloudDataStore(tokenInterceptor), NotificationDataStore {

    private val notificationService: NotificationService = connector
            .getServiceCreator()
            .create(NotificationService::class.java)

    override fun getNotifications(notificationParamProvider: NotificationParamProvider): Observable<PagerEntity<List<NotificationEntity>>> {
        return notificationService.getNotifications(notificationParamProvider.optionalParam.map)
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    notificationService.getNotifications(notificationParamProvider.optionalParam.map)
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }))
    }

    override fun deleteNotification(notificationParamProvider: NotificationParamProvider): Observable<NotificationEntity> {
        return notificationService.deleteNotification(notificationParamProvider.getNotificationId())
                .flatMap { dataResponse ->
                    ResponseFlatResult.flatResult(dataResponse)
                }
                .onErrorResumeNext(tokenInterceptor.refreshTokenAndRetry(Observable.defer {
                    notificationService.deleteNotification(notificationParamProvider.getNotificationId())
                            .flatMap { dataResponse ->
                                ResponseFlatResult.flatResult(dataResponse)
                            }
                }))
    }
}