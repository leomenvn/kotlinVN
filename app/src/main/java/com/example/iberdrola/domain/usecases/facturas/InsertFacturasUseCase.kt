package com.example.iberdrola.domain.usecases.facturas

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class InsertFacturasUseCase (private val repository: FacturaRepository){

    suspend operator fun invoke(list: List<Factura>){
        repository.insertAllFacturas(list)
    }
}