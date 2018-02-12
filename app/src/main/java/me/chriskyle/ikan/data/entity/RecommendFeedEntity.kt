package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/17.
 */
data class RecommendFeedEntity(@SerializedName("name")
                              var name: String? = null,
                              @SerializedName("feeds")
                              var feeds: List<FeedEntity>? = null)
