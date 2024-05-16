package com.example.iberdrola.domain.data.model

data class Filtro(
    var fechaMin: String,
    var fechaMax: String,
    var estado: HashMap<String, Boolean>,
    var monto: Double
) {
    constructor() : this("0001-01-01", "9999-12-31", HashMap<String, Boolean>().apply {
        put("Pagada", false)
        put("Anuladas", false)
        put("Cuota fija", false)
        put("Plan de pago", false)
        put("Pendiente de pago", false)
    }, 0.0)
}