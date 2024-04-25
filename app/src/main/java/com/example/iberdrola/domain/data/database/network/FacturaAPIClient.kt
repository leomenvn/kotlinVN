package com.example.iberdrola.domain.data.database.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.iberdrola.domain.data.model.DetallesResponse
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.FacturaResponse
import retrofit2.Response
import retrofit2.http.*

interface FacturaAPIClient {

    @GET("facturas")
    suspend fun getDataFromAPI(): Response<FacturaResponse>

    @Mock
    @MockResponse(body = "{   \"numFacturas\": 8,   \"facturas\": [     {       \"descEstado\": \"MOCK1\",       \"importeOrdenacion\": 18.0999,       \"fecha\": \"07/02/2019\"     },      {       \"descEstado\": \"MOCK2\",       \"importeOrdenacion\": 33.333,       \"fecha\": \"05/02/2019\"     }   ]    }")
    @GET("/")
    suspend fun getDataFromMock(): Response<FacturaResponse>

    @Mock
    @MockResponse(body = "{   \"cau\": \"ES00330000000298TR1FA555\",   \"estado\": \"No hay solicitud de autoconsumo\",   \"tipo\": \"Con excentes y sin compensación\",   \"compensacion\": \"Precio PVPC\",   \"potencia\": \"33kWp\" }")
    @GET("/")
    suspend fun getDetalles(): Response<DetallesResponse>
}
