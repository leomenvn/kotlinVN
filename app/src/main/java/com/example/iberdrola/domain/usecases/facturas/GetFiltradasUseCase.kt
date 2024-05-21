package com.example.iberdrola.domain.usecases.facturas

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class GetFiltradasUseCase (private val repository: FacturaRepository){

    suspend operator fun invoke(estado: HashMap<String, Boolean>, monto: Double, fechaMin: String, fechaMax: String): List<Factura> {
        return repository.getFiltradas(estado, monto, fechaMin, fechaMax)
    }
}