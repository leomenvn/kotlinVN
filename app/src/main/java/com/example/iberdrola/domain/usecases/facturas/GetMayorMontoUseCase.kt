package com.example.iberdrola.domain.usecases.facturas

import com.example.iberdrola.domain.data.FacturaRepository

class GetMayorMontoUseCase(private val repository: FacturaRepository) {

    suspend operator fun invoke(): Double {
        return repository.getMayorMonto()
    }
}