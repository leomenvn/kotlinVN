package com.example.iberdrola.ui.facturas

import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.MyApplication
import com.example.iberdrola.databinding.FragmentFacturasFiltroBinding
import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.database.IberdrolaDatabase
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.Filtro
import com.example.iberdrola.domain.usecases.GetFacturasUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FacturasViewModel: ViewModel() {

    // VARIABLES DE LA LISTA
    private lateinit var database: IberdrolaDatabase
    private lateinit var repository: FacturaRepository

    private val _retromock = MutableLiveData<Boolean>()
    val retromock: LiveData<Boolean>
        get() = _retromock

    private val _factModel = MutableLiveData<List<Factura>?>()
    val factModel: MutableLiveData<List<Factura>?>
        get() = _factModel

    private var getFacturasUseCase = GetFacturasUseCase()


    // VARIABLES DEL FILTRADO
    private lateinit var binding: FragmentFacturasFiltroBinding
    private val calendar = Calendar.getInstance(Locale.getDefault())
    private var filtro: Filtro = Filtro()

    private val _fechaMin = MutableLiveData<String>()
    val fechaMin: LiveData<String>
        get() = _fechaMin

    private val _fechaMax = MutableLiveData<String>()
    val fechaMax: LiveData<String>
        get() = _fechaMax

    private val _estado = MutableLiveData<HashMap<String,Boolean>>()
    val estado: LiveData<HashMap<String,Boolean>>
        get() = _estado

    private val _monto = MutableLiveData<Double>()
    val monto: LiveData<Double>
        get() = _monto

    private val _sbEstado = MutableLiveData<Int>()
    val sbEstado: LiveData<Int>
        get() = _sbEstado

    fun setSB(progress: Int) {
        _sbEstado.value = progress
    }


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
            Log.e("LISTA VM","Error al obtener las facturas: ${e.message}")
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
                         Log.e("LISTA VM","LLAMADA A API")
                     }else{
                         _factModel.value = emptyList()
                         Log.e("LISTA VM","LISTA VACIA, NINGUNA DE LAS DOS")
                     }
                }else{
                 _factModel.value = llamarBDD()
                 Log.e("LISTA VM","LLAMADA A BDD")
                }
             }
         }
    }

    fun actualizarMock(boolean: Boolean) {
        _retromock.value = boolean
    }



    // FUNCIONES PARA EL FILTRADO
    fun escogerFecha(btFecha: Button, context: Context, mode: Boolean) {
        val datePicker = DatePickerDialog(context, { _, year, month, dayOfMonth ->

            // Fecha escogida
            val fecha = Calendar.getInstance()
            fecha.set(year, month, dayOfMonth)

            // Formatear fecha para el botn y la BD
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            btFecha.text = formato.format(fecha.time)
            if(mode) _fechaMin.value = btFecha.text.toString() else _fechaMax.value = btFecha.text.toString()

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        // Restringir fecha máxima
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }

    fun escogerMonto(progress: Int) {
        val aux = progress.toDouble() * 2.5
        val limite = when {
            aux < 5 -> 5.0
            aux > 250 -> 250.0
            else -> aux
        }
        _sbEstado.value = progress
        _monto.value = limite
    }

    fun comprobarCB(listaCB: List<CheckBox>) {
        listaCB.forEach { cb ->
            cb.setOnCheckedChangeListener { _, isChecked ->
                val estadoActual = _estado.value ?: HashMap()
                estadoActual[cb.text.toString()] = isChecked
                _estado.postValue(estadoActual)
            }
        }
    }

    fun aplicarFiltro() {
        filtro.fechaMin = _fechaMin.value
        filtro.fechaMax = _fechaMax.value
        filtro.estado = _estado.value
        filtro.monto = _monto.value
        Log.d("FILTRO:", filtro.toString())
    }

    fun borrarFiltro() {
        _fechaMax.value = ""
        _fechaMin.value = ""
        _monto.value = 5.0
        _sbEstado.value = 0
        filtro = Filtro()

    }
}

