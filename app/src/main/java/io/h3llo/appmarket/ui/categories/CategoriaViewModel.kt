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

    fun obtenerCategorias(token: String) {

        viewModelScope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    Api.build().obtenerCategorias( "Bearer $token")
                }

                if(response.isSuccessful){
                    _categories.value = response.body()?.data
                }else{
                    // TODO 401 popup Tokem vencido
                }

            } catch (ex:Exception){
                val error = ex.message

            } finally{

            }
        }

    }
}