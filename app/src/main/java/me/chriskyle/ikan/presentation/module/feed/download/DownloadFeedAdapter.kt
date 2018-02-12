package me.chriskyle.ikan.presentation.module.feed.download

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadLargeFileListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.model.FileDownloadStatus
import com.liulishuo.filedownloader.util.FileDownloadUtils
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.FeedEntity
import me.chriskyle.ikan.presentation.Router
import java.io.File

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/27.
 */
class DownloadFeedAdapter(val context: Context) : RecyclerView.Adapter<DownloadFeedViewHolder>() {

    var onFeedDeleteClickListener: OnFeedDeleteClickListener? = null
    private var taskDownloadListener = object : FileDownloadLargeFileListener() {

        private fun checkCurrentHolder(task: BaseDownloadTask?): DownloadFeedViewHolder? {
            val tag = task!!.tag as DownloadFeedViewHolder
            return if (tag.id != task.id) {
                null
            } else tag
        }

        override fun started(task: BaseDownloadTask?) {
            super.started(task)
            val tag = checkCurrentHolder(task) ?: return

            tag.taskStatus?.setText(R.string.tasks_manager_status_started)
        }

        override fun connected(task: BaseDownloadTask?, etag: String?, isContinue: Boolean, soFarBytes: Long, totalBytes: Long) {
            val tag = checkCurrentHolder(task) ?: return

            tag.updateDownloading(FileDownloadStatus.connected.toInt(), soFarBytes, totalBytes)
            tag.taskStatus?.setText(R.string.tasks_manager_status_connected)
        }

        override fun error(task: BaseDownloadTask, e: Throwable) {
            e.printStackTrace()
            val tag = checkCurrentHolder(task) ?: return

            tag.updateNotDownloaded(FileDownloadStatus.error.toInt(), task.largeFileSoFarBytes, task.largeFileTotalBytes)
            DownloadManager.getImpl(context)?.removeTaskForViewHolder(task.id)
        }

        override fun pending(task: BaseDownloadTask, soFarBytes: Long, totalBytes: Long) {
            val tag = checkCurrentHolder(task) ?: return

            tag.updateDownloading(FileDownloadStatus.pending.toInt(), soFarBytes, totalBytes)
            tag.taskStatus?.setText(R.string.tasks_manager_status_pending)
        }

        override fun progress(task: BaseDownloadTask, soFarBytes: Long, totalBytes: Long) {
            val tag = checkCurrentHolder(task) ?: return

            tag.updateDownloading(FileDownloadStatus.progress.toInt(), soFarBytes, totalBytes)
        }

        override fun paused(task: BaseDownloadTask, soFarBytes: Long, totalBytes: Long) {
            val tag = checkCurrentHolder(task) ?: return

            tag.updateNotDownloaded(FileDownloadStatus.paused.toInt(), soFarBytes, totalBytes)
            tag.taskStatus?.setText(R.string.tasks_manager_status_paused)
            DownloadManager.getImpl(context)!!.removeTaskForViewHolder(task.id)
        }

        override fun warn(task: BaseDownloadTask) {}

        override fun completed(task: BaseDownloadTask) {
            val tag = checkCurrentHolder(task) ?: return

            tag.updateDownloaded()
            DownloadManager.getImpl(context)!!.removeTaskForViewHolder(task.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadFeedViewHolder {
        return DownloadFeedViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_download_feed, parent, false))
    }

    override fun onBindViewHolder(holder: DownloadFeedViewHolder, position: Int) {
        val downloadModel = DownloadManager.getImpl(context)!![position]

        holder.update(downloadModel.id, position)
        holder.taskAction?.tag = holder
        holder.thumbnail?.imageView?.setImageURI(downloadModel.thumbnail)
        holder.taskName?.text = downloadModel.name

        DownloadManager.getImpl(context)!!.updateViewHolder(holder.id, holder)

        holder.delete?.setOnClickListener {
            DownloadManager.getImpl(context)!!.removeTaskForViewHolder(downloadModel.id)
            onFeedDeleteClickListener?.onFeedDeleteClick(downloadModel)
        }

        holder.taskAction?.isEnabled = true
        holder.taskAction?.setOnClickListener { bindAction(holder, position) }

        if (DownloadManager.getImpl(context)!!.isReady) {
            val status = DownloadManager.getImpl(context)!!.getStatus(downloadModel.id, downloadModel.path)
            if (status == FileDownloadStatus.pending.toInt() || status == FileDownloadStatus.started.toInt() ||
                    status == FileDownloadStatus.connected.toInt()) {
                // start task, but file not onViewAttached yet
                holder.updateDownloading(status, DownloadManager.getImpl(context)!!.getSoFar(downloadModel.id),
                        DownloadManager.getImpl(context)!!.getTotal(downloadModel.id))
            } else if (!File(downloadModel.path).exists() && !File(FileDownloadUtils.getTempPath(downloadModel.path)).exists()) {
                // not exist file
                holder.updateNotDownloaded(status, 0, 0)
            } else if (DownloadManager.getImpl(context)!!.isDownloaded(status)) {
                // already downloaded and exist
                holder.updateDownloaded()
            } else if (status == FileDownloadStatus.progress.toInt()) {
                // downloading
                holder.updateDownloading(status, DownloadManager.getImpl(context)!!.getSoFar(downloadModel.id),
                        DownloadManager.getImpl(context)!!.getTotal(downloadModel.id))
            } else {
                // not start
                holder.updateNotDownloaded(status, DownloadManager.getImpl(context)!!.getSoFar(downloadModel.id),
                        DownloadManager.getImpl(context)!!.getTotal(downloadModel.id))
            }
        } else {
            holder.taskStatus?.setText(R.string.tasks_manager_status_loading)
            holder.taskAction?.isEnabled = false
        }
    }

    override fun getItemCount() = DownloadManager.getImpl(context)!!.taskCounts

    private fun bindAction(holder: DownloadFeedViewHolder, position: Int) {
        val actionDes = holder.taskAction?.text
        if (actionDes === null) {
            return
        }

        when (actionDes) {
            holder.itemView.context.resources.getString(R.string.pause) ->
                FileDownloader.getImpl().pause(holder.id)
            holder.itemView.context.resources.getString(R.string.start) -> {
                val model = DownloadManager.getImpl(context)!![holder.position]
                val task = FileDownloader.getImpl().create(model.url)
                        .setPath(model.path)
                        .setCallbackProgressTimes(100)
                        .setListener(taskDownloadListener)

                DownloadManager.getImpl(context)!!.addTaskForViewHolder(task)
                DownloadManager.getImpl(context)!!.updateViewHolder(holder.id, holder)

                task.start()
            }
            holder.itemView.context.resources.getString(R.string.play) -> {
                holder.taskAction?.isEnabled = true

                Router.routeToVideoPlayer(context, FeedEntity(DownloadManager.getImpl(context)!![holder.position].path!!,
                        DownloadManager.getImpl(context)!![holder.position].thumbnail!!,
                        DownloadManager.getImpl(context)!![holder.position].name!!))
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        val taskSparseArray = DownloadManager.getImpl(context)!!.getAllDownloadTasks()

        var index = taskSparseArray.size()
        while (index > 0) {
            taskSparseArray[index - 1].listener = null
            index -= 1
        }

        super.onDetachedFromRecyclerView(recyclerView)
    }

    interface OnFeedDeleteClickListener {

        fun onFeedDeleteClick(downloadEntity: DownloadEntity)
    }
}
