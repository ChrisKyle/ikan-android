package me.chriskyle.ikan.data.repository.datastore.cloud

import me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor.TokenInterceptor

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
abstract class BaseCloudDataStore(val tokenInterceptor: TokenInterceptor)
