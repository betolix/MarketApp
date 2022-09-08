package io.h3llo.appmarket.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.Api
import io.h3llo.appmarket.model.CrearCuentaRequest
import io.h3llo.appmarket.model.Genero
import io.h3llo.appmarket.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterViewModel : ViewModel() {

    private var _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var _generos = MutableLiveData<List<Genero>>()
    val generos: LiveData<List<Genero>> = _generos

    private var _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    fun obtenerGeneros() {
        viewModelScope.launch {
            _loader.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    Api.build().obtenerGeneros()
                }
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _generos.value = it
                    }
                } else {
                    _error.value = response.message()
                }
            } catch (ex: Exception) {
                _error.value = ex.message.toString()

            } finally {
                _loader.value = false
            }
        }

    }

    fun registrarUsuario(request: CrearCuentaRequest) {

        viewModelScope.launch {
            _loader.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    Api.build().crearCuenta(request)
                }
                if (response.isSuccessful) {
                    if (response.body()?.success == true) {
                        _usuario.value = response.body()?.data
                    } else {
                        _error.value = response.body()?.message
                    }
                } else {
                    _error.value = response.message()
                }
            } catch (ex: Exception) {
                _error.value = ex.message
            } finally {
                _loader.value = false
            }
        }

    }


}