package me.chriskyle.ikan.presentation.module.feed.upload

import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class UploadPresenterImpl(private val bus: Bus, private val feedRepositoryManager: FeedRepositoryManager)
    : BasePresenter<UploadView>(), UploadPresenter