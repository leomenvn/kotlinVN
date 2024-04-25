package com.example.iberdrola.ui.ss.fragments

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.database.IberdrolaDatabase
import com.example.iberdrola.domain.usecases.GetFacturasUseCase
import kotlinx.coroutines.launch

class DetallesFragmentViewModel: ViewModel() {

    private lateinit var database: IberdrolaDatabase
    private lateinit var repository: FacturaRepository

    private var getFacturasUseCase = GetFacturasUseCase()

    init {
        initRepository()
    }

    private fun initRepository() {
        database = IberdrolaDatabase.getDatabase()
        repository = FacturaRepository(database)
    }

    fun actualizarDetalles(etCau: EditText,etState: EditText, etTipo: EditText,etCompensacion: EditText,etPotencia: EditText) {
        viewModelScope.launch{
            val detalles = getFacturasUseCase.invokeDetalles(repository)

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