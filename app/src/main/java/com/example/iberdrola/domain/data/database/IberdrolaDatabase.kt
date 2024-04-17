package com.example.iberdrola.domain.data.database

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.iberdrola.domain.data.database.dao.FacturaDAO
import kotlin.concurrent.Volatile


abstract class IberdrolaDatabase : RoomDatabase() {

    abstract fun FacturaDAO(): FacturaDAO?

    companion object {
        @Volatile
        private var INSTANCE: IberdrolaDatabase? = null

        fun getInstance(context: Context): IberdrolaDatabase? {
            if (INSTANCE == null) {
                synchronized(IberdrolaDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,

                            IberdrolaDatabase::class.java, "Iberdrola_Database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}