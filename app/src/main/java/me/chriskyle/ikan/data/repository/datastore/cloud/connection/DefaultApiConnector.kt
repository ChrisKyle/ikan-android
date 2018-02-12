package me.chriskyle.ikan.data.repository.datastore.cloud.connection

import me.chriskyle.library.net.connection.BaseApiConnector
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
@Singleton
class DefaultApiConnector @Inject constructor(interceptor: Interceptor) :
        BaseApiConnector(interceptor, NetworkConfig.REQUEST_TIME_OUT_DURATION) {

    override val apiBaseUrl
        get() = NetworkConfig.API_BAE_URL
}
