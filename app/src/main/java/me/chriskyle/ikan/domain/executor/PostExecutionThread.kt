package me.chriskyle.ikan.domain.executor

import io.reactivex.Scheduler

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/17.
 */
interface PostExecutionThread {

    val scheduler: Scheduler
}