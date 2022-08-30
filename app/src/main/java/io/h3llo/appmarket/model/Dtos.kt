package io.h3llo.appmarket.model

import com.google.gson.annotations.SerializedName

data class LoginDto (

    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("data")
    val data:Usuario
)  {
}