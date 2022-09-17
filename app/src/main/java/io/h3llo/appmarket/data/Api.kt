package io.h3llo.appmarket.data

import com.google.gson.Gson
import io.h3llo.appmarket.model.*
import io.h3llo.appmarket.util.Constantes
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

object  Api {

    // URL COMPLETA https://marketapp2021.herokuapp.com/api/usuarios/login
    // URL BASE: https://marketapp2021.herokuapp.com/
    // METODO: /api/usuarios/login

    private val interceptor = HttpLoggingInterceptor()
    private val okHttpClient = OkHttpClient.Builder()

    init{
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(interceptor)
    }

    // 1. CREAR INSTANCIA DE RETROFIT
    private val builder : Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constantes.URL_BASE)
        .client(okHttpClient.build() )
        .addConverterFactory(GsonConverterFactory.create())

    // 2. DEFINIR LOS METODOS QUE USAREMOS (INTERFACES)
    interface ApiInterface {
        // DEFINE EL QUE VAS A HACER
        @POST("api/usuarios/login")
        suspend fun autenticar(@Body request:LoginRequest) : Response<LoginDto>

        @GET("/api/usuarios/obtener-generos")
        suspend fun obtenerGeneros() : Response<GeneroDto>

        @POST("/api/usuarios/crear-cuenta")
        suspend fun crearCuenta(@Body request: CrearCuentaRequest) : Response<LoginDto>

        @GET("api/categorias")
        suspend fun obtenerCategorias(@Header("Authorization") authorization:String):Response<CategoriaDto>
    }

    // 3. DEVOLVER LA INSTANCIA
    fun build(): ApiInterface{
        // INTERCEPTOR
        // var httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
        // httpClient.addInterceptor(interceptor())
        return builder.build().create(ApiInterface::class.java)
    }

    // INTERCEPTOR
    /*
    fun interceptor(): HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    } */


}