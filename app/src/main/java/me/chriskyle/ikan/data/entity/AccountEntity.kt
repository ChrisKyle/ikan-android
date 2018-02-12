package me.chriskyle.ikan.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
data class AccountEntity(@SerializedName("id")
                         var id: Long = 0,
                         @SerializedName("nickname")
                         var nickname: String = "",
                         @SerializedName("avatar")
                         var avatarUrl: String = "",
                         @SerializedName("gender")
                         var gender: String = "",
                         @SerializedName("balance")
                         var balance: Long = 0) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(nickname)
        writeString(avatarUrl)
        writeString(gender)
        writeLong(balance)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AccountEntity> = object : Parcelable.Creator<AccountEntity> {
            override fun createFromParcel(source: Parcel): AccountEntity = AccountEntity(source)
            override fun newArray(size: Int): Array<AccountEntity?> = arrayOfNulls(size)
        }
    }
}