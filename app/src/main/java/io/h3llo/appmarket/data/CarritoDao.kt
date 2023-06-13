package io.h3llo.appmarket.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.h3llo.appmarket.model.Carrito

@Dao
interface CarritoDao {

    @Insert
    fun insert(carrito: Carrito) : Long

    @Update
    fun update(carrito: Carrito)

    @Delete
    fun delete(carrito: Carrito)

    // SELECT CON FILTRO WHERE
    //    @Query( "select * from tabla_carrito where descripcion=:DES order by uuid desc")
    //    fun getProducts(DES:String):List<Carrito>


    @Query( "select * from tabla_carrito  order by uuid desc")
    fun getProducts(): LiveData<List<Carrito>>




}