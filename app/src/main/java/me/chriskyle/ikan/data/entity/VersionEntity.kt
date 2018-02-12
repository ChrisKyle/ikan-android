package me.chriskyle.ikan.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
data class VersionEntity(@SerializedName("code")
                         var code: Int = 0,
                         @SerializedName("name")
                         var name: String = "",
                         @SerializedName("update_log")
                         var updateLog: String = "",
                         @SerializedName("url")
                         var url: String = "",
                         @SerializedName("size")
                         var size: String = "",
                         @SerializedName("md5")
                         var md5: String = "",
                         @SerializedName("is_constraint")
                         var isConstraint: Boolean = false) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(code)
        writeString(name)
        writeString(updateLog)
        writeString(url)
        writeString(size)
        writeString(md5)
        writeInt((if (isConstraint) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VersionEntity> = object : Parcelable.Creator<VersionEntity> {
            override fun createFromParcel(source: Parcel): VersionEntity = VersionEntity(source)
            override fun newArray(size: Int): Array<VersionEntity?> = arrayOfNulls(size)
        }
    }
}
