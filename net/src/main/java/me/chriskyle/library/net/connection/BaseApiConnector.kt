package me.chriskyle.library.net.connection

import me.chriskyle.library.net.BuildConfig
import me.chriskyle.library.net.request.interceptor.HttpLoggingInterceptor
import me.chriskyle.library.toolkit.log.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
abstract class BaseApiConnector(private val interceptor: Interceptor,
                                private val timeoutDuration: Long) : Connector {

    protected abstract val apiBaseUrl: String

    override fun getServiceCreator(): Retrofit = createServiceCreator()

    private fun createServiceCreator() = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    open fun createHttpBuilder() = OkHttpClient.Builder()

    private fun createHttpClient(): OkHttpClient {
        val httpClientBuilder = createHttpBuilder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Logger.d(message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.connectTimeout(timeoutDuration, TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(interceptor)

        return httpClientBuilder.build()
    }
}
