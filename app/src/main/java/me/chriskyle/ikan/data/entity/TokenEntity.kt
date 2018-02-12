package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
data class TokenEntity(@SerializedName("token")
                       var token: String? = null,
                       @SerializedName("refreshToken")
                       var reToken: String? = null)