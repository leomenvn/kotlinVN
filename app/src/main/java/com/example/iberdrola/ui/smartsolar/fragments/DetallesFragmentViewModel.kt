package com.example.iberdrola.ui.smartsolar.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.data_retrofit.response.DetallesResponse
import com.example.iberdrola.domain.usecases.GetDetallesUseCase
import kotlinx.coroutines.launch

class DetallesFragmentViewModel: ViewModel() {

    private var getDetallesUseCase = GetDetallesUseCase()

    private val _detalles = MutableLiveData<DetallesResponse>()
    val detalles: MutableLiveData<DetallesResponse>
        get() = _detalles

    init {
        viewModelScope.launch{
            detalles.value = getDetallesUseCase.invoke()
        }
    }
}