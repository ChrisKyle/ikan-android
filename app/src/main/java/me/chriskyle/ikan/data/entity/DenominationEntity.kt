package me.chriskyle.ikan.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
data class DenominationEntity(@SerializedName("amount")
                              var amount: Int,
                              @SerializedName("payment_code")
                              var paymentCode: String,
                              @SerializedName("qr_code")
                              var qrCode: String)