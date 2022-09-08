package io.h3llo.appmarket.core

import android.content.Context
import android.widget.Toast

object Message {

    fun Context.toast(mensaje:String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

}