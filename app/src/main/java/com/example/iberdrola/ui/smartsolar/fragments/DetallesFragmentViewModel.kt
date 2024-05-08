package com.example.iberdrola.ui.smartsolar.fragments

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.domain.usecases.GetDetallesUseCase
import kotlinx.coroutines.launch

class DetallesFragmentViewModel: ViewModel() {

    private var getDetallesUseCase = GetDetallesUseCase()

    fun actualizarDetalles(etCau: EditText,etState: EditText, etTipo: EditText,etCompensacion: EditText,etPotencia: EditText) {
        viewModelScope.launch{
            val detalles = getDetallesUseCase.invoke()

            if (detalles != null) {
                etCau.setText(detalles.cau)
                etState.setText(detalles.estado)
                etTipo.setText(detalles.tipo)
                etCompensacion.setText(detalles.compensacion)
                etPotencia.setText(detalles.cau)
            }
        }
    }
}