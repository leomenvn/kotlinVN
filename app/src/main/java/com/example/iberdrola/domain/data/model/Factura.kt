package com.example.iberdrola.domain.data.model

import java.util.Date

data class Factura(
    var id : Int,
    var descripcion: String,
    var monto: Double,
    var fechaCreacion: Date,
    var fechaPago: Date,
    var pendiente: Boolean){
}