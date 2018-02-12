package me.chriskyle.ikan.presentation.module.feed.download

import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface DownloadPresenter : MvpLcePresenter<DownloadView> {

    fun destroyDownloader()

    fun loadDownloadFeeds()

    fun deleteHistory(downloadEntity: DownloadEntity)
}