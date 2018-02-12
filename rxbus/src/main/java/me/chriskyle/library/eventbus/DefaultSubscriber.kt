package me.chriskyle.library.eventbus

import io.reactivex.Scheduler
import io.reactivex.functions.Consumer

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
open class DefaultSubscriber<T : Any> constructor(eventClass: Class<T>,
                                             private var receiver: Consumer<T>?)
    : AbstractSubscriber<T>() {

    private val hashCode = receiver?.hashCode()

    internal var eventClass: Class<T>? = eventClass
        private set
    internal var scheduler: Scheduler? = null
        private set

    fun withScheduler(scheduler: Scheduler): DefaultSubscriber<T> {
        this.scheduler = scheduler
        return this
    }

    override fun acceptEvent(event: T) {
        receiver!!.accept(event)
    }

    override fun release() {
        eventClass = null
        receiver = null
        scheduler = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        val that = other as DefaultSubscriber<*>?

        return receiver == that!!.receiver
    }

    override fun hashCode() = hashCode!!
}
