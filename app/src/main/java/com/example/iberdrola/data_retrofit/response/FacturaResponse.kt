package com.example.iberdrola.data_retrofit.response

import com.example.iberdrola.domain.data.model.Factura

data class FacturaResponse (
    var numFacturas: Int,
    var facturas: List<Factura>
    )