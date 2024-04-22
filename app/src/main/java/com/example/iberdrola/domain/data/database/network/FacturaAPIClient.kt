package com.example.iberdrola.domain.data.database.network

import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.FacturaResponse
import retrofit2.Response
import retrofit2.http.*

interface FacturaAPIClient {

    @GET("facturas")
    suspend fun getDataFromAPI(): Response<FacturaResponse>

}
