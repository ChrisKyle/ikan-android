package me.chriskyle.ikan.domain.executor.impl

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import javax.inject.Inject
import javax.inject.Singleton

import me.chriskyle.ikan.domain.executor.ThreadExecutor

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/17.
 */
@Singleton
class DefaultExecutor @Inject constructor() : ThreadExecutor {

    private val workQueue: BlockingQueue<Runnable>
    private val threadPoolExecutor: ThreadPoolExecutor
    private val threadFactory: ThreadFactory

    init {
        workQueue = LinkedBlockingQueue()
        threadFactory = DefaultThreadFactory()
        threadPoolExecutor = ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory)
    }

    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    private class DefaultThreadFactory : ThreadFactory {

        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }

        companion object {

            private const val THREAD_NAME = "android_thread_"
        }
    }

    companion object {

        private const val CORE_POOL_SIZE = 5
        private const val MAX_POOL_SIZE = 20
        private const val KEEP_ALIVE_TIME = 10

        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}
