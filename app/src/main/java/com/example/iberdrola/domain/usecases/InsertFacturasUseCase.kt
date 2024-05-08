package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class InsertFacturasUseCase (private val repository: FacturaRepository = FacturaRepository.getInstance()){

    suspend operator fun invoke(list: List<Factura>){
        repository.insertAllFacturas(list)
    }
}