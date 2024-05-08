package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class GetFiltradasUseCase (private val repository: FacturaRepository = FacturaRepository.getInstance()){

    suspend operator fun invoke(estado: String, monto: Double, fechaMin: String, fechaMax: String): List<Factura> {
        return repository.getFiltradas(estado, monto, fechaMin, fechaMax)
    }
}