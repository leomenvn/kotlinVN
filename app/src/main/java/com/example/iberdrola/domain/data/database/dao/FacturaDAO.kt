package com.example.iberdrola.domain.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iberdrola.domain.data.database.entities.FacturaEntity

@Dao
interface FacturaDAO {

    @Query("SELECT * FROM TABLA_FACTURAS")
    suspend fun getAllFacturas():List<FacturaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFacturas(facturas:List<FacturaEntity>)

    @Query("DELETE FROM TABLA_FACTURAS")
    suspend fun deleteAllFacturas()
}