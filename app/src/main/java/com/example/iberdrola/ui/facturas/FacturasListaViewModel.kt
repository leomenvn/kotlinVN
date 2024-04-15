package com.example.iberdrola.ui.facturas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.usecases.GetFacturasUseCase
import kotlinx.coroutines.launch

class FacturasListaViewModel: ViewModel() {

    private val _factModel = MutableLiveData<List<Factura>>()
    val factModel: LiveData<List<Factura>>
        get() = _factModel
    var getFacturasUseCase = GetFacturasUseCase()

     fun onCreate() {

        viewModelScope.launch {
            try {
                val result = getFacturasUseCase() // Llama a la función para obtener las facturas
                _factModel.value = result ?: emptyList() // Asigna el resultado al LiveData
            } catch (e: Exception) {
                Log.e("FacturasListaViewModel", "Error al obtener las facturas: ${e.message}")
            }
        }
    }
}

