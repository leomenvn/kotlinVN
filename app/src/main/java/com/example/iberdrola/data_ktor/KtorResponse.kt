package com.example.iberdrola.data_ktor

import com.example.iberdrola.domain.data.model.Factura
import kotlinx.serialization.Serializable

@Serializable
data class KtorResponse (
    var numFacturas: Int,
    var facturas: List<Factura>
)