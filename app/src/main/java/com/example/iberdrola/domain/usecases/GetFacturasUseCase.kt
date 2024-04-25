package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.DetallesResponse
import com.example.iberdrola.domain.data.model.Factura

class GetFacturasUseCase {

    suspend operator fun invoke(repository: FacturaRepository, mode: Boolean): List<Factura>? {
        if(mode){
            return repository.getAllFacturasAPI(false)
        }
        return repository.getAllFacturasDB()
    }

    suspend fun invokeMock(repository: FacturaRepository): List<Factura>? {
        return repository.getAllFacturasAPI(true)
    }

    suspend fun invokeDetalles(repository: FacturaRepository): DetallesResponse? {
        return repository.getDetalles()
    }
}