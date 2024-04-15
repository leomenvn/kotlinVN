package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class GetFacturasUseCase {

    private val repository = FacturaRepository()

    suspend operator fun invoke() : List<Factura>? {
        return repository.getAllFacturas()
    }

}