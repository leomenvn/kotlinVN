package com.example.iberdrola.domain.data.database.network

import android.util.Log
import com.example.iberdrola.core.RetrofitHelper
import com.example.iberdrola.core.RetromockHelper
import com.example.iberdrola.domain.data.model.DetallesResponse
import com.example.iberdrola.domain.data.model.Factura

class FacturaService {

    private val retrofit = RetrofitHelper.getRetrofit()
    private val retromock = RetromockHelper.getRetromock()

    suspend fun getDataAPI(mode: Boolean): List<Factura>? {
        var response = retrofit.create(FacturaAPIClient::class.java).getDataFromAPI()

        if(mode){
            response = retromock.create(FacturaAPIClient::class.java).getDataFromMock()
        }

        return if (response.isSuccessful) {
            val listaFacturas = response.body()?.facturas
            if (listaFacturas.isNullOrEmpty()) {
                emptyList()
            } else {
                listaFacturas
            }
        } else {
            Log.d("FALLO EN LA RESPUESTA", response.toString())
            null
        }
    }

    suspend fun getDetallesAPI(): DetallesResponse? {
        val response = retromock.create(FacturaAPIClient::class.java).getDetalles()

        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.d("FALLO EN LA RESPUESTA", response.toString())
            null
        }
    }
}