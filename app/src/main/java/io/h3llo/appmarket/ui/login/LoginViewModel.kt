package io.h3llo.appmarket.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.Api
import io.h3llo.appmarket.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    fun auth(email: String, password: String) {

        viewModelScope.launch {
            // HILO MAIN

            // INDICAR A LA VISTA QUE MUESTRE EL PROGRESS


            try{
                val response = withContext(Dispatchers.IO){
                    // HILO APARTE - TAREAS DE DURACION LARGA
                    Api.build().autenticar(LoginRequest(email,password,""))
                }

                // HILO MAIN
                if(response.isSuccessful){ // 200
                    val loginDto = response.body()
                    // LET
                    loginDto?.let {
                        // Toast.makeText(requireContext(),response.code().toString()+ " " + it.message, Toast.LENGTH_SHORT).show()
                    }
                }else{ // 400, 401, 500
                    if(response.code() == 401){ // NO AUTORIZADO

                    } else {

                    }
                    // Toast.makeText(requireContext(),response.code().toString() + " " + response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception){
                // Toast.makeText(requireContext(), ex.message,Toast.LENGTH_SHORT).show()
            }

        }

    }

}