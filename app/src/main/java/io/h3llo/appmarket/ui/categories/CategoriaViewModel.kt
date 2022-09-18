package io.h3llo.appmarket.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.Api
import io.h3llo.appmarket.model.Categoria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriaViewModel : ViewModel() {

    private var _categories = MutableLiveData<List<Categoria>>()
    val categories : LiveData<List<Categoria>> = _categories

    private var _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private var _loader = MutableLiveData<Boolean>()
    val loader : LiveData<Boolean> = _loader

    private var _authorized = MutableLiveData<String>()
    val authorized : LiveData<String> = _authorized

    fun obtenerCategorias(token: String) {

        viewModelScope.launch {

            _loader.value = true

            try{
                val response = withContext(Dispatchers.IO){
                    Api.build().obtenerCategorias( "Bearer $token")
                }

                if(response.isSuccessful){
                    _categories.value = response.body()?.data
                }else{
                    // TODO 401 popup Token vencido
                    if(response.code() == 401){
                        _authorized.value = "Tu sesion ha expirado. Vuelva a ingresar"

                    }else{
                        // ERROR
                        _error.value = response.message()
                    }
                }

            } catch (ex:Exception){
                _error.value = ex.message

            } finally{
                _loader.value = false
            }
        }

    }
}