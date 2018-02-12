package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2018/1/18.
 */
data class LoginEntity(@SerializedName("token")
                       var token: String,
                       @SerializedName("refresh_token")
                       var refreshToken: String,
                       @SerializedName("account")
                       var account: AccountEntity)