package me.chriskyle.ikan.presentation.module.feed.download

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.chriskyle.ikan.data.repository.datastore.local.db.DbHelper
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.rxcupboard.RxCupboard
import me.chriskyle.library.rxcupboard.RxDatabase
import java.lang.ref.WeakReference

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class DownloadPresenterImpl : BasePresenter<DownloadView>(), DownloadPresenter,
        OnDownloadStatusChangedListener {

    private lateinit var cupboard: RxDatabase

    override fun onViewAttached() {
        super.onViewAttached()

        cupboard = RxCupboard.withDefault(DbHelper.getConnection(view?.ctx))

        DownloadManager.getImpl(view?.ctx!!)!!.init()
        DownloadManager.getImpl(view?.ctx!!)!!.bindDownloadStatusChangedListener(WeakReference(this))
    }

    override fun destroyDownloader() {
        DownloadManager.getImpl(view?.ctx!!)!!.unbindDownloadStatusChangedListener(this)
        DownloadManager.getImpl(view?.ctx!!)!!.onDestroy()
    }

    override fun loadDownloadFeeds() {
        addSubscription(cupboard.query(DownloadEntity::class.java)
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { downloadModels ->
                    DownloadManager.getImpl(view?.ctx!!)?.addDownloadItems(downloadModels)
                })
    }

    override fun deleteHistory(downloadEntity: DownloadEntity) {
        cupboard.delete(downloadEntity)
        loadDownloadFeeds()
    }

    override fun refresh() {
    }

    override fun loadmore() {
    }

    override fun reload() {
    }

    override fun onDownloadStatusChanged() {
        view?.downloadDataChanged()
    }
}