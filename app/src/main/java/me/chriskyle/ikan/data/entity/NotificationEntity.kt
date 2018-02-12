package me.chriskyle.ikan.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/16.
 */
data class NotificationEntity(@SerializedName("id")
                              var id: Long = 0,
                              @SerializedName("type")
                              var type: Int? = null,
                              @SerializedName("title")
                              var title: String? = null,
                              @SerializedName("content")
                              var content: String? = null,
                              @SerializedName("url")
                              var url: String? = null,
                              @SerializedName("create_time")
                              var createTime: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeValue(type)
        writeString(title)
        writeString(content)
        writeString(url)
        writeString(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<NotificationEntity> = object : Parcelable.Creator<NotificationEntity> {
            override fun createFromParcel(source: Parcel): NotificationEntity = NotificationEntity(source)
            override fun newArray(size: Int): Array<NotificationEntity?> = arrayOfNulls(size)
        }
    }
}
