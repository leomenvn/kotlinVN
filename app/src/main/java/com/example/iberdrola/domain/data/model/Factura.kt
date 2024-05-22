package com.example.iberdrola.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Factura(
    var descEstado : String,
    var importeOrdenacion: Double,
    var fecha: String,
)