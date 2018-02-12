package me.chriskyle.library.net.connection

import retrofit2.Retrofit

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
interface Connector {

    fun getServiceCreator(): Retrofit
}
