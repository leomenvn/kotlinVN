package com.example.iberdrola.domain.data.database

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.iberdrola.MyApplication
import com.example.iberdrola.domain.data.database.dao.FacturaDAO
import com.example.iberdrola.domain.data.database.entities.FacturaEntity

@Database(entities = [FacturaEntity::class], version = 1)
abstract class IberdrolaDatabase : RoomDatabase() {

    abstract fun getDAOInstance(): FacturaDAO

    companion object {
        @Volatile // Avoid concurrency issues
        private var _INSTANCE: IberdrolaDatabase? = null

        fun getDatabase(): IberdrolaDatabase {
            return _INSTANCE ?: synchronized(this) {
                buildDatabase().also { db -> _INSTANCE = db }
            }
        }

        private fun buildDatabase(): IberdrolaDatabase {
            try {
                return Room.databaseBuilder(
                    MyApplication.context, IberdrolaDatabase::class.java, "IberdrolaDatabase"
                ).build()
            }catch(e: Exception) {
                // Manejar la excepción aquí
                Log.e("IberdrolaDatabase", "Error al construir la base de datos: ${e.message}")
                throw e // O lanzar una excepción personalizada, dependiendo de tu lógica
            }

        }
    }

}