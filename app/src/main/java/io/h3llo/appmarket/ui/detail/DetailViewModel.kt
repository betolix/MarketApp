package io.h3llo.appmarket.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.AppDatabase
import io.h3llo.appmarket.model.Carrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel : ViewModel() {

    private var _loader = MutableLiveData<Boolean>()
    val loader : LiveData<Boolean> = _loader
    
    private var _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private var _responseSuccess = MutableLiveData<String>()
    val responseSuccess : LiveData<String> = _responseSuccess

    fun addToCart(cart: Carrito) {

        _loader.value=true

        viewModelScope.launch {

            try {
                val resultado = withContext(Dispatchers.IO){
                    AppDatabase.instance?.carritoDao()?.insert(cart)
                }

                if(resultado?.toInt()!! > 0){
                    _responseSuccess.value = "Producto agregado"
                }else{
                    _error.value = "Tuvimos problemas al procesae. Intente nuevamente"
                }

            }catch (ex: Exception){
                _error.value=ex.message
            }finally{
                _loader.value=false

            }
        }

    }

}