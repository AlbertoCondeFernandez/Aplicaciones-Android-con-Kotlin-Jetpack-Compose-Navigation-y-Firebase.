package com.example.basededatos.localdb

//import androidx.room.Insert
//import androidx.room.Query
import androidx.room.*

@Dao
interface SesionDao {
    //Aquí van las Querys con las operaciones que se quieran realizar.

    //Iniciar sesión
    @Insert
    fun nuevaSesion(user: SesionData)

    //Consultar inicio de sesión
    @Query("SELECT * FROM ${Estructura.Sesion.TABLE_NAME}")
    fun getEstadoSesion(): SesionData?
}
