package io.h3llo.appmarket.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.Api
import io.h3llo.appmarket.model.LoginRequest
import io.h3llo.appmarket.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private var _loader : MutableLiveData<Boolean> = MutableLiveData()
    val loader : LiveData<Boolean> = _loader

    private var _error : MutableLiveData<String> = MutableLiveData()
    val error : LiveData<String> = _error
    
    private val _user = MutableLiveData<Usuario>()
    val user : LiveData<Usuario> = _user
    
    private val _token = MutableLiveData<String>()
    val token : LiveData<String> = _token

    fun auth(email: String, password: String) {

        viewModelScope.launch(Dispatchers.Main) {
            // HILO MAIN

            // INDICAR A LA VISTA QUE MUESTRE EL PROGRESS - USANDO LiveData
            _loader.value = true

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
                        if(loginDto.success){
                             _token.value = loginDto.token
                            _user.value = loginDto.data

                        }else{
                            _error.value = loginDto.message
                        }
                    }
                }else{ // 400, 401, 500
                    _error.value = response.message()
                    if(response.code() == 401){ // NO AUTORIZADO

                    } else {

                    }
                    // Toast.makeText(requireContext(),response.code().toString() + " " + response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception){
                _error.value = ex.message
                // Toast.makeText(requireContext(), ex.message,Toast.LENGTH_SHORT).show()
            } finally {
                _loader.value = false
            }

        }

    }

}