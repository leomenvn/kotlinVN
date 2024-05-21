package com.example.iberdrola.ui.smartsolar.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.data_retrofit.response.DetallesResponse
import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.usecases.GetDetallesUseCase
import kotlinx.coroutines.launch

class DetallesFragmentViewModel(rep: FacturaRepository): ViewModel() {

    private var getDetallesUseCase = GetDetallesUseCase(rep)

    private val _detalles = MutableLiveData<DetallesResponse>()
    val detalles: MutableLiveData<DetallesResponse>
        get() = _detalles

    init {
        viewModelScope.launch{
            detalles.value = getDetallesUseCase.invoke()
        }
    }
}