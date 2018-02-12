package me.chriskyle.library.eventbus

import io.reactivex.functions.Consumer

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
interface Bus {

    fun subscribe(observer: Any)

    fun <T : Any> subscribe(observer: Any, subscriber: DefaultSubscriber<T>)

    fun unSubscribe(observer: Any)

    fun <T : Any> obtainSubscriber(eventClass: Class<T>, receiver: Consumer<T>): DefaultSubscriber<T>

    fun post(event: Any)
}
