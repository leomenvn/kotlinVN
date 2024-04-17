package com.example.iberdrola.domain.data

import com.example.iberdrola.domain.data.database.dao.FacturaDAO
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.FacturaResponse
import com.example.iberdrola.domain.data.network.FacturaService
import retrofit2.Call
import retrofit2.Response

class FacturaRepository {

    private val api = FacturaService()

    suspend fun getAllFacturasAPI(): List<Factura>? {
        return api.getDataAPI()
    }

    suspend fun getAllFacturasDatabase(): List<Factura>? {
        return null
    }
}