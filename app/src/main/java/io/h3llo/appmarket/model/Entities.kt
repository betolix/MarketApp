package io.h3llo.appmarket.model

data class Usuario (
    val uuid:String,
    val nombres:String,
    val apellidos:String,
    val email:String,
    val celular:String,
    val genero:String,
    val nroDoc:String,
    val tipo:String,
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