package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/12/28.
 */
class PagerEntity<T>(@SerializedName("page") var page: Int = 0,
                     @SerializedName("total") var total: Int = 0,
                     @SerializedName("list") var list: T? = null)