package io.h3llo.appmarket.data

import com.google.gson.Gson
import io.h3llo.appmarket.model.LoginDto
import io.h3llo.appmarket.model.LoginRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class Api {

    // URL COMPLETA https://marketapp2021.herokuapp.com/api/usuarios/login
    // URL BASE: https://marketapp2021.herokuapp.com/
    // METODO: /api/usuarios/login


    // 1. CREAR INSTANCIA DE RETROFIT
    private val builder : Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://marketapp2021.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())

    // 2. DEFINIR LOS METODOS QUE USAREMOS (INTERFACES)
    interface ApiInterface {
        // DEFINE EL QUE VAS A HACER
        @POST("api/usuarios/login")
        fun autenticar(@Body request:LoginRequest) : Call<LoginDto>
    }

    // 3. DEVOLVER LA INSTANCIA
    fun build(): ApiInterface{
        return builder.build().create(ApiInterface::class.java)

    }


}