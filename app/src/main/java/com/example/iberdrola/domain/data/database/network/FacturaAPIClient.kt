package com.example.iberdrola.domain.data.database.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.iberdrola.domain.data.model.DetallesResponse
import com.example.iberdrola.domain.data.model.FacturaResponse
import retrofit2.Response
import retrofit2.http.*

interface FacturaAPIClient {

    @GET("facturas")
    suspend fun getDataFromAPI(): Response<FacturaResponse>

    @Mock
    @MockResponse(body="mockFacturas.json")
    @GET("/")
    suspend fun getDataFromMock(): Response<FacturaResponse>

    @Mock
    @MockResponse(body="mockDetalles.json")
    @GET("/")
    suspend fun getDetalles(): Response<DetallesResponse>
}
