package com.example.iberdrola.domain.usecases.facturas

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class GetFacturasUseCase (private val repository: FacturaRepository = FacturaRepository.getInstance()) {

    suspend operator fun invoke(n: Int): List<Factura>? {
        return when (n) {
            1 -> repository.getAllFacturasAPI(true)
            2 -> repository.getAllFacturasAPI(false)
            3 -> repository.getAllFacturasKtor()
            else -> emptyList()
        }
    }
}