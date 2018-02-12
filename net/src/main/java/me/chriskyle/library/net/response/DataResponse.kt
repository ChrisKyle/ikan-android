package me.chriskyle.library.net.response

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
data class DataResponse<T>(@SerializedName("status_code") var statusCode: Int = 0,
                           @SerializedName("msg") var msg: String? = null,
                           @SerializedName("data") var data: T? = null)