package io.h3llo.appmarket.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.Api
import io.h3llo.appmarket.model.Producto
import io.h3llo.appmarket.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel:ViewModel() {

    private var _loader : MutableLiveData<Boolean> = MutableLiveData()
    val loader : LiveData<Boolean> = _loader

    private var _error : MutableLiveData<String> = MutableLiveData()
    val error : LiveData<String> = _error

    private var _authorized = MutableLiveData<String>()
    val authorized : LiveData<String> = _authorized

    private var _products = MutableLiveData<List<Producto>>()
    val products : LiveData<List<Producto>> = _products
    
    fun getProducts(token: String, uuid: String) {

        viewModelScope.launch {
            _loader.value = true

            try {
                val response = withContext(Dispatchers.IO){
                    Api.build().obtenerProductos("Bearer $token", uuid)
                }
                if(response.isSuccessful){
                    _products.value = response.body()?.data

                }else{
                    if(response.code() == 401){
                        _authorized.value = "Tu sesion ha expirado. Vuelva a ingresar"
                    }
                    _error.value = response.message()
                }
            }catch (ex:Exception){
                _error.value = ex.message
            }finally {
                _loader.value = false
            }
        }

    }

}