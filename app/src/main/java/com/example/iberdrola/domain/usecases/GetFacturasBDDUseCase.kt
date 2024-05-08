package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura


class GetFacturasBDDUseCase(private val repository: FacturaRepository = FacturaRepository.getInstance()){

    suspend operator fun invoke(): List<Factura> {
        return repository.getAllFacturasDB()
    }

}