package io.h3llo.appmarket.core

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.h3llo.appmarket.databinding.EditProductDialogBinding
import io.h3llo.appmarket.databinding.MessageDialogBinding

object Message {

    fun Context.toast(mensaje:String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    // TODO IMPLEMENTAR EL ALERT DIALOG
    fun Context.showMessageAlertDialog(message:String):AlertDialog{

        val binding = MessageDialogBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)

        val alertDialog = builder.create()

        binding.tvTitle.text = "$message"

        binding.btnDone.setOnClickListener{
            alertDialog.dismiss()

        }
        return alertDialog
    }
}