package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
data class AssetsEntity(@SerializedName("balance")
                        var balance: Long)