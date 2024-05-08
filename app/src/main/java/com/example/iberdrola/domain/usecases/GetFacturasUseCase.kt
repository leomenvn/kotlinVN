package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class GetFacturasUseCase (private val repository: FacturaRepository = FacturaRepository.getInstance()) {

    suspend operator fun invoke(mode: Boolean): List<Factura>? {
        return if (mode) {
            repository.getAllFacturasAPI(true)
        } else {
            repository.getAllFacturasAPI(false)
        }
    }
}