package me.chriskyle.ikan.presentation.module.feed.download

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

import com.liulishuo.filedownloader.model.FileDownloadStatus

import me.chriskyle.ikan.R
import me.chriskyle.library.support.squareprogressbar.SquareProgressBar

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/27.
 */
class DownloadFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var position: Int = 0
    internal var id: Int = 0

    @BindView(R.id.task_name_tv)
    @JvmField
    var taskName: TextView? = null

    @BindView(R.id.task_status_tv)
    @JvmField
    var taskStatus: TextView? = null

    @BindView(R.id.thumbnail)
    @JvmField
    var thumbnail: SquareProgressBar? = null

    @BindView(R.id.download_action)
    @JvmField
    internal var taskAction: Button? = null

    @BindView(R.id.delete)
    @JvmField
    var delete: View? = null

    init {
        ButterKnife.bind(this, itemView)
        initViews()
    }

    fun update(id: Int, position: Int) {
        this.id = id
        this.position = position
    }

    fun updateDownloaded() {
        thumbnail?.setProgress(0)

        taskStatus?.setText(R.string.tasks_manager_status_completed)
        taskAction?.setText(R.string.play)
    }

    fun updateNotDownloaded(status: Int, sofar: Long, total: Long) {
        if (sofar > 0 && total > 0) {
            val percent = sofar / total.toFloat()
            thumbnail?.setProgress((percent * 100).toInt())
        } else {
            thumbnail?.setProgress(0)
        }

        when (status) {
            FileDownloadStatus.error.toInt() -> taskStatus?.setText(R.string.tasks_manager_status_error)
            FileDownloadStatus.paused.toInt() -> taskStatus?.setText(R.string.tasks_manager_status_paused)
            else -> taskStatus?.setText(R.string.tasks_manager_status_not_downloaded)
        }
        taskAction?.setText(R.string.start)
    }

    fun updateDownloading(status: Int, sofar: Long, total: Long) {
        val percent = sofar / total.toFloat()
        thumbnail?.setProgress((percent * 100).toInt())

        when (status) {
            FileDownloadStatus.pending.toInt() -> taskStatus?.setText(R.string.tasks_manager_status_pending)
            FileDownloadStatus.started.toInt() -> taskStatus?.setText(R.string.tasks_manager_status_started)
            FileDownloadStatus.connected.toInt() -> taskStatus?.setText(R.string.tasks_manager_status_connected)
            FileDownloadStatus.progress.toInt() -> taskStatus?.setText(R.string.tasks_manager_status_progress)
            else -> taskStatus?.text = itemView.context.getString(
                    R.string.tasks_manager_status_downloading, status)
        }

        taskAction?.setText(R.string.pause)
    }

    private fun initViews() {
        thumbnail?.setImage(R.drawable.feed_thumbnail_default)
        thumbnail?.setColor("#f1b664")
        thumbnail?.isRoundedCorners = true
        thumbnail?.width = 5
    }
}
