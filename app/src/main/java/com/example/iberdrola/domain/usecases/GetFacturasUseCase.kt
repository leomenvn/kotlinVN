package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura

class GetFacturasUseCase {

    suspend operator fun invoke(repository: FacturaRepository, mode: Boolean): List<Factura>? {
        if(mode){
            return repository.getAllFacturasAPI()
        }
        return repository.getAllFacturasDB()
    }
}