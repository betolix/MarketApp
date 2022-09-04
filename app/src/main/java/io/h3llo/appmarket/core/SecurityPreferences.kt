package io.h3llo.appmarket.core

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import io.h3llo.appmarket.util.Constantes

object SecurityPreferences {

    // 1 ENCRIPTAR PREFERENCIA
    fun Context.encryptPreferences(name:String):SharedPreferences{
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC )
        return EncryptedSharedPreferences.create(
            name,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // 2 GRABAR TOKEN
    fun saveToken(token:String, encryptedSharedPreferences: SharedPreferences){
        encryptedSharedPreferences.edit().putString(Constantes.TOKEN_KEY, token).apply()
    }

    // 3 OBTENER TOKEN
    fun getToken(encryptedSharedPreferences:SharedPreferences):String{
        return encryptedSharedPreferences.getString(Constantes.TOKEN_KEY,"") ?: ""
    }
}