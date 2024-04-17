package com.example.iberdrola.domain.data.network

import android.util.Log
import com.example.iberdrola.core.RetrofitHelper
import com.example.iberdrola.domain.data.model.Factura

class FacturaService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getDataAPI(): List<Factura>? {
        val response = retrofit.create(FacturaAPIClient::class.java).getDataFromAPI()
        if (response.isSuccessful) {
            val listaFacturas = response.body()?.facturas
            if (listaFacturas.isNullOrEmpty()) {
                return emptyList()
            } else {
                return listaFacturas
            }
        } else {
            Log.d("FALLO", response.toString())
            return null
        }
    }
}