package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
data class CommentEntity(@SerializedName("content")
                         var content: String,
                         @SerializedName("create_time")
                         var createTime: String,
                         @SerializedName("account")
                         var account: AccountEntity)