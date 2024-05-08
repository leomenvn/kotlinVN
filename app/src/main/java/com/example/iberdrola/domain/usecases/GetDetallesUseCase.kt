package com.example.iberdrola.domain.usecases

import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.DetallesResponse

class GetDetallesUseCase (private val repository: FacturaRepository = FacturaRepository.getInstance()){

    suspend operator fun invoke(): DetallesResponse? {
        return repository.getDetalles()
    }
}