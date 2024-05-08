package com.example.iberdrola.domain.data.database.entities.network

import android.util.Log
import com.example.iberdrola.data.RetrofitHelper
import com.example.iberdrola.core.RetromockHelper
import com.example.iberdrola.domain.data.model.DetallesResponse
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.FacturaResponse
import retrofit2.Response

class FacturaService {

    private val retrofit = RetrofitHelper.getInstance()
    private val retromock = RetromockHelper.getIntance()


    suspend fun getDataAPI(mode: Boolean): List<Factura>? {

        val response: Response<FacturaResponse> = if(mode){
            retromock.create(FacturaAPIClient::class.java).getDataFromMockAPI()
        }else{
            retromock.create(FacturaAPIClient::class.java).getDataFromMock()
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