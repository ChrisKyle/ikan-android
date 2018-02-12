package me.chriskyle.library.net.request.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/18.
 */
abstract class BaseNetworkRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val headerParamsMap = injectParamIntoHeader(HashMap())
        val requestOrigin = chain.request()
        val headersOrigin = requestOrigin.headers()
        val headerBuilder = headersOrigin.newBuilder()

        for ((key, value) in headerParamsMap) {
            headerBuilder.set(key, value)
        }

        val request = requestOrigin.newBuilder().headers(headerBuilder.build()).build()
        return chain.proceed(request)
    }

    open fun injectParamIntoHeader(headerParamsMap: MutableMap<String, String>): MutableMap<String, String> = headerParamsMap
}
