package me.chriskyle.library.eventbus

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.ObjectHelper
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
internal class RxBus : Bus {

    private val bus = PublishSubject.create<Any>().toSerialized()

    override fun subscribe(observer: Any) {
        val observerClass = observer.javaClass

        if (OBSERVERS.putIfAbsent(observerClass, CompositeDisposable()) != null)
            OBSERVERS.remove(observerClass)
    }

    override fun <T : Any> subscribe(observer: Any, subscriber: DefaultSubscriber<T>) {
        (SUBSCRIBERS).putIfAbsent(observer.javaClass, CopyOnWriteArraySet())
        val subscribers = SUBSCRIBERS[observer.javaClass]

        if (subscribers!!.contains(subscriber))
            throw IllegalArgumentException("Subscribe has already been subscribed.")
        else
            subscribers.add(subscriber)

        val observable = bus.ofType(subscriber.eventClass)
                .observeOn(if (subscriber.scheduler == null) AndroidSchedulers.mainThread() else subscriber.scheduler)

        val observerClass = observer.javaClass

        OBSERVERS.putIfAbsent(observerClass, CompositeDisposable())
        val composite = OBSERVERS[observerClass]
        composite?.add(observable.subscribe(subscriber))
    }

    override fun unSubscribe(observer: Any) {
        val composite = OBSERVERS[observer.javaClass]
        composite?.let {
            composite.dispose()
            OBSERVERS.remove(observer.javaClass)

            val subscribers = SUBSCRIBERS[observer.javaClass]
            subscribers?.let {
                subscribers.clear()
                SUBSCRIBERS.remove(observer.javaClass)
            }
        }
    }

    override fun <T : Any> obtainSubscriber(eventClass: Class<T>, receiver: Consumer<T>): DefaultSubscriber<T> {
        return DefaultSubscriber(eventClass, receiver)
    }

    override fun post(event: Any) {
        bus.onNext(event)
    }

    companion object {

        val OBSERVERS = ConcurrentHashMap<Class<*>, CompositeDisposable>()
        val SUBSCRIBERS = ConcurrentHashMap<Class<*>, CopyOnWriteArraySet<DefaultSubscriber<*>>>()
    }
}
