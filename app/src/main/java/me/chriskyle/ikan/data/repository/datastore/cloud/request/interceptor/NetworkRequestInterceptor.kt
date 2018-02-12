package me.chriskyle.ikan.data.repository.datastore.cloud.request.interceptor

import android.content.Context
import android.text.TextUtils
import me.chriskyle.ikan.data.entity.TokenEntity
import me.chriskyle.ikan.data.repository.datastore.local.cache.TokenCache
import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.interceptor.BaseNetworkRequestInterceptor

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/19.
 */
class NetworkRequestInterceptor(private val context: Context, private val tokenCache: TokenCache) :
        BaseNetworkRequestInterceptor() {

    private var tokenEntity: TokenEntity? = null

    override fun injectParamIntoHeader(headerParamsMap: MutableMap<String, String>): MutableMap<String, String> {
        tokenEntity = tokenCache.get()

        if (tokenEntity != null && !TextUtils.isEmpty(tokenEntity?.token)) {
            headerParamsMap[Constants.KPI.TOKEN] = tokenEntity?.token!!
        }

        return headerParamsMap
    }
}
