package com.example.iberdrola.domain.data

import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.FacturaResponse
import com.example.iberdrola.domain.data.network.FacturaService
import retrofit2.Call
import retrofit2.Response

class FacturaRepository {

    private val api = FacturaService()

    suspend fun getAllFacturas(): List<Factura>? {
        return api.getDataAPI()
    }
}