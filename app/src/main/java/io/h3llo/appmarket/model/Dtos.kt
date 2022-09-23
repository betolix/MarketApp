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
    val data:Usuario)  {
}

data class GeneroDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Genero>
)

data class CategoriaDto(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data:List<Categoria>
)

data class ProductoDto(
    val data: List<Producto>,
    val message: String,
    val success: Boolean
)