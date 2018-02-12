package me.chriskyle.ikan.presentation.module.feed.download

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/27.
 */
data class DownloadEntity(var id: Int = 0,
                          var name: String? = null,
                          var url: String? = null,
                          var thumbnail: String? = null,
                          var path: String? = null)
