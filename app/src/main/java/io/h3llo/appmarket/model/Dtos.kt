package io.h3llo.appmarket.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

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

@Entity(tableName = "tabla_carrito")
data class Carrito(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="uuid")
    val uuid: String,

    @ColumnInfo(name="descripcion")
    val descripcion: String,

    @ColumnInfo(name="precio")
    val precio:Double,

    @ColumnInfo(name="cantidad")
    var cantidad:Int,

    @ColumnInfo(name="imagen")
    val imagen:String,

    @ColumnInfo(name="codigoCategoria")
    val codigoCategoria: String
)



















