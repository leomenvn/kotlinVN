package com.example.iberdrola.domain.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FacturaEntity::class], version = 1)
abstract class IberdrolaDatabase: RoomDatabase() {

    abstract fun getDAOInstance(): FacturaDAO

}