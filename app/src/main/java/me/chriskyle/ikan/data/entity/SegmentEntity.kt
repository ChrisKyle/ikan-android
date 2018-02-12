package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2018/1/27.
 */
class SegmentEntity {

    @SerializedName("id")
    var id: Long = 0
    @SerializedName("name")
    var name: String = ""
}