package me.chriskyle.ikan.domain.repository.manager

import me.chriskyle.ikan.domain.executor.PostExecutionThread
import me.chriskyle.ikan.domain.executor.ThreadExecutor

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/17.
 */
abstract class BaseRepositoryManager(internal val threadExecutor: ThreadExecutor,
                                     internal val postExecutionThread: PostExecutionThread)
