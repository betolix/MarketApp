package io.h3llo.appmarket.model

data class LoginRequest (val email:String, val password:String, val firebaseToken:String="" )

data class CrearCuentaRequest (
    val nombres: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val celular: String,
    val genero: String,
    val nroDoc: String,
    val firebaseToken: String="",
        )

