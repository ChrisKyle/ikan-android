package me.chriskyle.ikan.presentation.module.feed.watchhistory

import me.chriskyle.ikan.data.entity.WatchHistoryEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface OnDeleteClickListener {

    fun onDeleteClick(watchHistoryEntity: WatchHistoryEntity)
}