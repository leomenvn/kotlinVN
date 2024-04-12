package com.example.iberdrola.domain.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.iberdrola.domain.data.database.dao.FacturaDAO
import com.example.iberdrola.domain.data.database.entities.FacturaEntity

@Database(entities = [FacturaEntity::class], version = 1)
abstract class IberdrolaDatabase: RoomDatabase() {

    abstract fun getFacturaDao():FacturaDAO

}