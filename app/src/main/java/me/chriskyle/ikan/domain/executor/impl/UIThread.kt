package me.chriskyle.ikan.domain.executor.impl

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import me.chriskyle.ikan.domain.executor.PostExecutionThread

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/17.
 */
@Singleton
class UIThread @Inject constructor() : PostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}
