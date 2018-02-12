package me.chriskyle.library.eventbus

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
abstract class AbstractSubscriber<T : Any> : Consumer<T>, Disposable {

    @Volatile
    private var disposed: Boolean = false

    override fun accept(event: T) {
        try {
            acceptEvent(event)
        } catch (e: Exception) {
            throw RuntimeException("Could not accept event: " + event.javaClass, e)
        }
    }

    override fun dispose() {
        if (!disposed) {
            disposed = true
            release()
        }
    }

    override fun isDisposed() = disposed

    protected abstract fun acceptEvent(event: T)

    protected abstract fun release()
}
