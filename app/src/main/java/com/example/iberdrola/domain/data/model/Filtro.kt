package com.example.iberdrola.domain.data.model

data class Filtro(
    var fechaMin: String?,
    var fechaMax: String?,
    var estado : HashMap<String, Boolean>?,
    var monto: Double?
) {
    constructor() : this(null, null, HashMap<String, Boolean>().apply {
        put("Pagada", false)
        put("Anuladas", false)
        put("Cuota fija", false)
        put("Pendiente de pago", false)
        put("Plan de pago", false)
    }, null)
}
