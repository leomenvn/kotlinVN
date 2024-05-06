package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.DetallesResponse

class GetDetallesUseCase {

    suspend fun invoke(repository: FacturaRepository): DetallesResponse? {
        return repository.getDetalles()
    }
}