package io.h3llo.appmarket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OperacionViewModel : ViewModel() {

    // USAMOS LiveData o Flow
    // LIVEDATA : SOLO PERMITE OBTENER EL VALOR
    // MUTABLELIVEDATA : PERMITE MODIFICAR EL VALOR Y OBTENER EL VALOR

    var _number : MutableLiveData<Int> = MutableLiveData()
    var number : LiveData<Int> = _number

    fun getNumber(numberInput: Int){
        _number.value = numberInput

    }
}