package com.example.iberdrola.domain.data.network

import android.util.Log
import com.example.iberdrola.core.RetrofitHelper
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.FacturaResponse
import retrofit2.Call
import retrofit2.Response

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
            Log.d("Failure", response.toString())
            return null
        }
    }
}