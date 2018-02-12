package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
data class ProblemEntity(@SerializedName("id")
                            var id: Long,
                         @SerializedName("title")
                            var title: String = "",
                         @SerializedName("url")
                            var url: String = "")
