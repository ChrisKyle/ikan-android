package me.chriskyle.ikan.presentation.module.feed.watchhistory

import me.chriskyle.ikan.data.entity.WatchHistoryEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/12/04.
 */
interface OnItemClickListener {

    fun onItemClick(watchHistoryEntity: WatchHistoryEntity)
}