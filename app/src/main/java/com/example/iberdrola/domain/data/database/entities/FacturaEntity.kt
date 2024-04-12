package com.example.iberdrola.domain.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "TABLA_FACTURAS")
data class FacturaEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "fecha_creacion") val fechaCreacion: Date,
    @ColumnInfo(name = "fecha_pago") val fechaPago: Date?,
    @ColumnInfo(name = "monto") val monto: Double,
    @ColumnInfo(name = "descripcion") val desc: String?,
    @ColumnInfo(name = "pendiente") val pendiente: Boolean = false
)