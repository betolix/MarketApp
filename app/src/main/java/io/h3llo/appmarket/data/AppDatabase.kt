package io.h3llo.appmarket.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.h3llo.appmarket.model.Carrito

//@Database(entities=[Carrito::class,Usuario::Class])

@Database(entities=[Carrito::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // MAPEAR TODOS LOS DAOS (Data Access Objects)
    abstract fun carritoDao(): CarritoDao

    companion object{

         var instance : AppDatabase? = null

    // CREAR LA BASE DE DATOS


        fun getInstance(context: Context) : AppDatabase {
            if( instance == null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "BDMarket").build()
            }
            return instance as AppDatabase
        }

    }



}