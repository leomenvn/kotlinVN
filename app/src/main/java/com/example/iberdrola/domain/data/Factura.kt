package com.example.iberdrola.domain.data

import java.util.Date

data class Factura(
    var id : Int,
    var descripcion: String,
    var fechaCreacion: Date,
    var fechaPago: Date){
}