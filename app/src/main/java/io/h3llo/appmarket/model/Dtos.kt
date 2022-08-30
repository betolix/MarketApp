package io.h3llo.appmarket.model

data class LoginDto (
    val success:Boolean,
    val message: String,
    val token: String,
    val data:Usuario
)  {
}