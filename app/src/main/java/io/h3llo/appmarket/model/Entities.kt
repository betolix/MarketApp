package io.h3llo.appmarket.model

import com.google.gson.annotations.SerializedName

data class Usuario (
    @SerializedName("uuid")
    val uuid:String,
    @SerializedName("nombres")
    val nombres:String,
    @SerializedName("apellidos")
    val apellidos:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("celular")
    val celular:String,
    @SerializedName("genero")
    val genero:String,
    @SerializedName("nroDoc")
    val nroDoc:String,
    @SerializedName("tipo")
    val tipo:String,
)

data class Genero(
    @SerializedName("genero")
    val genero:String,
    @SerializedName("descripcion")
    val descripcion: String
){
    override fun toString(): String{
        return descripcion
    }
}

data class Categoria(
    @SerializedName("cover")
    val cover: String,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("uuid")
    val uuid:String
)

class Persona constructor(var nombres:String="", apellidos:String=""){
    constructor(apellidos: String):this("", apellidos)

    fun saludo(){

    }

    companion object{
        val saluda = "Hola"
        fun saludar(){

        }
    }
}