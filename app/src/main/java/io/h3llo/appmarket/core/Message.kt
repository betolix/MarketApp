package io.h3llo.appmarket.core

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object Message {

    fun Context.toast(mensaje:String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    // TODO IMPLEMENTAR EL ALERT DIALOG
    fun showMessage(){

    }
}