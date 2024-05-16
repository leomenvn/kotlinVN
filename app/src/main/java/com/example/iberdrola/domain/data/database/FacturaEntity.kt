package com.example.iberdrola.domain.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TABLA_FACTURAS")
data class FacturaEntity(
    @PrimaryKey val fechaCreacion: String,
    @ColumnInfo(name = "monto") val monto: Double,
    @ColumnInfo(name = "pendiente") val pendiente: String
)

