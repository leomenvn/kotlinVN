package com.example.iberdrola.ui.facturas

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.MyApplication
import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.database.IberdrolaDatabase
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.usecases.GetFacturasUseCase
import kotlinx.coroutines.launch

class FacturasListaViewModel: ViewModel() {

    private lateinit var database: IberdrolaDatabase
    private lateinit var repository: FacturaRepository

    private val _retromock = MutableLiveData<Boolean>()
    val retromock: LiveData<Boolean>
        get() = _retromock

    private val _factModel = MutableLiveData<List<Factura>?>()
    val factModel: MutableLiveData<List<Factura>?>
        get() = _factModel
    private var getFacturasUseCase = GetFacturasUseCase()

    init {
        initRepository()
    }

    private fun initRepository() {
        database = IberdrolaDatabase.getDatabase()
        repository = FacturaRepository(database)
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    private suspend fun llamarAPI() {
        try {
            _factModel.value = getFacturasUseCase.invoke(repository, true)
            _factModel.value?.let { repository.insertAllFacturas(it) }
        } catch (e: Exception) {
            Log.e("ViewModel API","Error al obtener las facturas: ${e.message}")
        }
    }


    private suspend fun llamarBDD(): List<Factura>? {
        return getFacturasUseCase.invoke(repository, false)
    }

    private suspend fun llamarRetromock(): List<Factura>?{
        return getFacturasUseCase.invokeMock(repository)
    }

     fun onCreate() {
         viewModelScope.launch{
             if(_retromock.value == true){
                 _factModel.value = llamarRetromock()
             }else{
                 if(repository.isEmpty()){
                     if (isNetworkAvailable(MyApplication.context)) {
                         llamarAPI()
                         Log.e("CABALGA","LLAMADA A API")
                     }else{
                         _factModel.value = emptyList()
                         Log.e("CABALGA","LISTA VACIA, NINGUNA DE LAS DOS")
                     }
                }else{
                 _factModel.value = llamarBDD()
                 Log.e("CABALGA","LLAMADA A BDD")
                }
             }
         }
    }

    fun actualizarMock(boolean: Boolean) {
        _retromock.value = boolean
    }
}

