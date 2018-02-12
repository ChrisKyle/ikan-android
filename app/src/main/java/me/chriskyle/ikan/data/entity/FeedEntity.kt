package me.chriskyle.ikan.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
data class FeedEntity(
        @SerializedName("url")
        var url: String = "",
        @SerializedName("thumbnail")
        var thumbnail: String = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("file_name")
        var fileName: String = "",
        @SerializedName("actors")
        var actors: List<ActorEntity> = mutableListOf(),
        @SerializedName("synopsis")
        var synopsis: String = "",
        @SerializedName("like_count")
        var likeCount: Long = 0,
        @SerializedName("watch_count")
        var watchCount: Long = 0,
        @SerializedName("diamond")
        var diamond: Int = 0,
        @SerializedName("duration")
        var duration: Long = 0,
        @SerializedName("type")
        var type: Int = 0,
        @SerializedName("account")
        var account: AccountEntity = AccountEntity(),
        @SerializedName("create_time")
        var createTime: String = "",
        @SerializedName("is_like")
        var isLike: Boolean = false,
        @SerializedName("is_bought")
        var isBought: Boolean = false,
        @SerializedName("id")
        var id: Long = -1) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.createTypedArrayList(ActorEntity.CREATOR),
            source.readString(),
            source.readLong(),
            source.readLong(),
            source.readInt(),
            source.readLong(),
            source.readInt(),
            source.readParcelable<AccountEntity>(AccountEntity::class.java.classLoader),
            source.readString(),
            1 == source.readInt(),
            1 == source.readInt(),
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
        writeString(thumbnail)
        writeString(title)
        writeString(fileName)
        writeTypedList(actors)
        writeString(synopsis)
        writeLong(likeCount)
        writeLong(watchCount)
        writeInt(diamond)
        writeLong(duration)
        writeInt(type)
        writeParcelable(account, 0)
        writeString(createTime)
        writeInt((if (isLike) 1 else 0))
        writeInt((if (isBought) 1 else 0))
        writeLong(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<FeedEntity> = object : Parcelable.Creator<FeedEntity> {
            override fun createFromParcel(source: Parcel): FeedEntity = FeedEntity(source)
            override fun newArray(size: Int): Array<FeedEntity?> = arrayOfNulls(size)
        }
    }
}

