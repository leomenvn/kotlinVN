package com.example.iberdrola.domain.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iberdrola.domain.data.database.FacturaEntity

@Dao
interface FacturaDAO {

    @Query("SELECT * FROM TABLA_FACTURAS")
    suspend fun getAllFacturas():List<FacturaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFacturas(facturas:List<FacturaEntity>)

    @Delete
    suspend fun delete(factura: FacturaEntity)

    @Query("DELETE FROM TABLA_FACTURAS")
    suspend fun deleteAllFacturas()

    @Query("SELECT * FROM TABLA_FACTURAS WHERE pendiente LIKE :estado")
    suspend fun getByPendiente(estado: String): List<FacturaEntity>

    @Query("SELECT * FROM TABLA_FACTURAS WHERE monto BETWEEN :min AND :max")
    suspend fun getByMonto(min: Double, max: Double): List<FacturaEntity>

    @Query("SELECT * FROM TABLA_FACTURAS " +
            "WHERE (pendiente LIKE :e1 OR pendiente LIKE :e2 OR pendiente LIKE :e3 OR pendiente LIKE :e4 OR pendiente LIKE :e5) " +
            "AND monto >= :monto " +
            "AND fechaCreacion BETWEEN :fechaMin AND :fechaMax")
    suspend fun getAllFiltradas(e1: String, e2: String, e3: String, e4: String, e5: String, monto: Double, fechaMin: String, fechaMax: String): List<FacturaEntity>

    @Query("SELECT * FROM TABLA_FACTURAS " +
            "WHERE monto >= :monto " +
            "AND fechaCreacion BETWEEN :fechaMin AND :fechaMax")
    suspend fun getFiltradas(monto: Double, fechaMin: String, fechaMax: String): List<FacturaEntity>

    @Query("SELECT MAX(monto) FROM TABLA_FACTURAS")
    suspend fun getMayorMonto(): Double

}