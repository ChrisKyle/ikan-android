package me.chriskyle.ikan.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/11/27.
 */
data class ActorEntity(@SerializedName("name")
                       var name: String = "",
                       @SerializedName("avatar_url")
                       var avatarUrl: String = "",
                       @SerializedName("role_playing")
                       var rolePlaying: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(avatarUrl)
        writeString(rolePlaying)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ActorEntity> = object : Parcelable.Creator<ActorEntity> {
            override fun createFromParcel(source: Parcel): ActorEntity = ActorEntity(source)
            override fun newArray(size: Int): Array<ActorEntity?> = arrayOfNulls(size)
        }
    }
}