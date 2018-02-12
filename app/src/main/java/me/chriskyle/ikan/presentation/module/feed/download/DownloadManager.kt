package me.chriskyle.ikan.presentation.module.feed.download

import android.content.Context
import android.text.TextUtils
import android.util.SparseArray
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadConnectListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.model.FileDownloadStatus
import com.liulishuo.filedownloader.util.FileDownloadUtils
import me.chriskyle.ikan.data.repository.datastore.local.db.DbHelper
import me.chriskyle.library.rxcupboard.RxCupboard
import me.chriskyle.library.rxcupboard.RxDatabase
import java.lang.ref.WeakReference

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/27.
 */
class DownloadManager private constructor(context: Context) {

    private val cupboard: RxDatabase = RxCupboard.withDefault(DbHelper.getConnection(context))
    private val taskSparseArray = SparseArray<BaseDownloadTask>()

    private var entityList: MutableList<DownloadEntity>? = mutableListOf()
    private var fileDownloadConnectListeners: SparseArray<FileDownloadConnectListener>? = null

    val isReady: Boolean
        get() = FileDownloader.getImpl().isServiceConnected

    val taskCounts: Int
        get() = entityList!!.size

    fun init() {
        if (!FileDownloader.getImpl().isServiceConnected) {
            FileDownloader.getImpl().bindService()
        }
    }

    fun onDestroy() {
        releaseTask()
    }

    fun addDownloadItems(entityList: MutableList<DownloadEntity>) {
        this.entityList = entityList
    }

    fun addTaskForViewHolder(task: BaseDownloadTask) {
        taskSparseArray.put(task.id, task)
    }

    fun removeTaskForViewHolder(id: Int) {
        taskSparseArray.remove(id)
    }

    fun updateViewHolder(id: Int, holder: DownloadFeedViewHolder) {
        val task = taskSparseArray.get(id) ?: return

        task.tag = holder
    }

    fun bindDownloadStatusChangedListener(onDownloadStatusChangedListener: WeakReference<OnDownloadStatusChangedListener>?) {
        if (fileDownloadConnectListeners == null) {
            fileDownloadConnectListeners = SparseArray()
        }

        val fileDownloadConnectListener = object : FileDownloadConnectListener() {

            override fun connected() {
                onDownloadStatusChangedListener?.get()?.onDownloadStatusChanged()
            }

            override fun disconnected() {
                onDownloadStatusChangedListener?.get()?.onDownloadStatusChanged()
            }
        }

        fileDownloadConnectListeners!!.put(onDownloadStatusChangedListener?.get()?.hashCode()!!,
                fileDownloadConnectListener)
        FileDownloader.getImpl().addServiceConnectListener(fileDownloadConnectListener)
    }

    fun unbindDownloadStatusChangedListener(onDownloadStatusChangedListener: OnDownloadStatusChangedListener) {
        if (fileDownloadConnectListeners == null) {
            return
        }

        val fileDownloadConnectListener = fileDownloadConnectListeners!!.get(onDownloadStatusChangedListener.hashCode())
        fileDownloadConnectListeners!!.remove(onDownloadStatusChangedListener.hashCode())
        FileDownloader.getImpl().removeServiceConnectListener(fileDownloadConnectListener)
    }

    fun getAllDownloadTasks() = taskSparseArray

    private fun releaseTask() {
        taskSparseArray.clear()
    }

    operator fun get(position: Int) = entityList!![position]

    private fun getById(id: Int): DownloadEntity? {
        return entityList!!.singleOrNull {
            it.id == id
        }
    }

    fun isDownloaded(status: Int) = status == FileDownloadStatus.completed.toInt()

    fun getStatus(id: Int, path: String?) = FileDownloader.getImpl().getStatus(id, path).toInt()

    fun getTotal(id: Int) = FileDownloader.getImpl().getTotal(id)

    fun getSoFar(id: Int) = FileDownloader.getImpl().getSoFar(id)

    @JvmOverloads
    fun addTask(name: String, url: String,thumbnail: String, path: String? = createPath(url)): DownloadEntity? {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null
        }

        val id = FileDownloadUtils.generateId(url, path)
        val model = getById(id)
        if (model != null) {
            return model
        }

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null
        }

        val newId = FileDownloadUtils.generateId(url, path)

        val newModel = DownloadEntity()
        newModel.id = newId
        newModel.name = name
        newModel.url = url
        newModel.path = path
        newModel.thumbnail = thumbnail

        cupboard.putDirectly(newModel)
        entityList!!.add(newModel)

        return newModel
    }

    private fun createPath(url: String): String? {
        return if (TextUtils.isEmpty(url)) {
            null
        } else FileDownloadUtils.getDefaultSaveFilePath(url)
    }

    companion object {

        @Volatile private var instance: DownloadManager? = null

        fun getImpl(context: Context): DownloadManager? {
            if (instance == null) {
                synchronized(DownloadManager::class.java) {
                    instance = DownloadManager(context)
                }
            }
            return instance
        }
    }
}
