package io.h3llo.appmarket.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.h3llo.appmarket.data.AppDatabase
import io.h3llo.appmarket.model.Carrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel : ViewModel() {

    private var _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private var _loader = MutableLiveData<Boolean>()
    val loader : LiveData<Boolean> = _loader

    private var _cart = MutableLiveData<List<Carrito>>()
    val cart : LiveData<List<Carrito>> = _cart

    private var _success = MutableLiveData<String>()
    val success : LiveData<String> = _success

    val getCart  = AppDatabase?.instance?.carritoDao()?.getProducts()

//    fun getCart() {
//        viewModelScope.launch {
//
//            _loader.value = true
//
//            try{
//                val response = withContext(Dispatchers.IO){
//                    AppDatabase.instance?.carritoDao()?.getProducts()
//                }
//
//                response?.let {
//                    _cart.value = it
//                }
//
//            }catch (ex: Exception){
//                _error.value = ex.message
//
//            }finally {
//
//                _loader.value = false
//
//            }
//        }
//    }

    fun delete(entity: Carrito) {

        viewModelScope.launch {

            _loader.value = true

            try{
                val response = withContext(Dispatchers.IO){
                    AppDatabase.instance?.carritoDao()?.delete(entity)
                }

                _success.value = "Producto eliminado"

            }catch (ex: Exception){
                _error.value = ex.message

            }finally {

                _loader.value = false

            }
        }

    }

    fun updateCart(entity: Carrito) {

        viewModelScope.launch {

            _loader.value = true

            try{
                val response = withContext(Dispatchers.IO){
                    AppDatabase.instance?.carritoDao()?.update(entity)
                }

                _success.value = "Producto actualizado"

            }catch (ex: Exception){
                _error.value = ex.message

            }finally {

                _loader.value = false

            }
        }

    }
}