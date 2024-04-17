package com.example.iberdrola.domain.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iberdrola.domain.data.database.entities.FacturaEntity
import com.example.iberdrola.domain.data.model.Factura

@Dao
interface FacturaDAO {

    @Query("SELECT * FROM TABLA_FACTURAS")
    suspend fun getAllFacturas():List<Factura>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFacturas(facturas:List<Factura>)

    @Delete
    suspend fun delete(factura: Factura)

    @Query("DELETE FROM TABLA_FACTURAS")
    suspend fun deleteAllFacturas()

    @Query("SELECT * FROM TABLA_FACTURAS WHERE pendiente LIKE :estado")
    suspend fun getByPendiente(estado: String): List<Factura>

    @Query("SELECT * FROM TABLA_FACTURAS WHERE monto BETWEEN :min AND :max")
    suspend fun getByMonto(min: Double, max: Double): List<Factura>

}