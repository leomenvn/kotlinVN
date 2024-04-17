package com.example.iberdrola.domain.data.database

import androidx.room.RoomDatabase
import com.example.iberdrola.domain.data.database.dao.FacturaDAO

abstract class IberdrolaDatabase : RoomDatabase() {

    abstract fun FacturaDAO(): FacturaDAO?

}