package com.example.iberdrola.ui.facturas

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.core.RemoteConfigHelper
import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.model.Filtro
import com.example.iberdrola.domain.usecases.facturas.GetFacturasBDDUseCase
import com.example.iberdrola.domain.usecases.facturas.GetFacturasUseCase
import com.example.iberdrola.domain.usecases.facturas.GetFiltradasUseCase
import com.example.iberdrola.domain.usecases.facturas.InsertFacturasUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.iberdrola.domain.usecases.facturas.GetMayorMontoUseCase

@SuppressLint("StaticFieldLeak")
class FacturasViewModel(private val remoteConfig: RemoteConfigHelper,
                        private val context: Context,
                        private val rep: FacturaRepository): ViewModel() {

    private var visibilidad: Boolean = true
    private var tipo: Int = 1

    private val _factModel = MutableLiveData<List<Factura>?>()
    val factModel: LiveData<List<Factura>?>
        get() = _factModel

    private val getFacturasUseCase = GetFacturasUseCase(rep)
    private val getFiltradasUseCase = GetFiltradasUseCase(rep)
    private val getFacturasBDDUseCase =  GetFacturasBDDUseCase(rep)
    private val insertFacturasUseCase = InsertFacturasUseCase(rep)
    private val getMayorMontoUseCase = GetMayorMontoUseCase(rep)


    // VARIABLES DEL FILTRADO
    private val calendar = Calendar.getInstance(Locale.getDefault())
    private var filtro = Filtro()

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

    var sbMax: Double = 100.0


    init {
        remote()
        _estado.value = HashMap<String, Boolean>().apply {
            put("Pagada", false)
            put("Anuladas", false)
            put("Cuota fija", false)
            put("Pendiente de pago", false)
            put("Plan de pago", false)
        }
        traerFacturas()
    }


    private fun remote(){
        viewModelScope.launch {
            if (isNetworkAvailable(context)) {
                remoteConfig.fetch()
                visibilidad = remoteConfig.getBoolean("listaVista")
            }
        }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }



     private fun traerFacturas(){
         var aux: List<Factura>? = emptyList()

         viewModelScope.launch {
             if(visibilidad) {
                 if(isNetworkAvailable(context)) {
                     aux = getFacturasUseCase(tipo)
                 }

                 aux?.let {
                     insertFacturasUseCase.invoke(it)
                 }
                 sbMax = getMayorMontoUseCase.invoke()
             }

             else {
                 sbMax = 0.0
             }
             _factModel.value = getFacturasBDDUseCase.invoke()
         }
     }


    fun setTipo(n: Int){
        tipo = n
        traerFacturas()
    }



    // ------------------------------------------------ //
    // --------------- FILTRADO ----------------------- //
    // ------------------------------------------------ //

    fun escogerFecha(mode: Boolean) {
        val datePicker = DatePickerDialog(context, { _, year, month, dayOfMonth ->

            // Fecha escogida
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
            if(mode){
                _fechaMin.value = fecha
                filtro.fechaMin = fechaBDD(fecha)
            } else{
                _fechaMax.value = fecha
                filtro.fechaMax = fechaBDD(fecha)
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }


    private fun fechaBDD(fecha: String): String{
        val aux = fecha.split("/")
        val dd = aux[0]
        val mm = aux[1]
        val yy = aux[2]
        return "$yy-$mm-$dd"
    }


    fun escogerMonto(progress: Int) {
        val pro = progress.coerceIn(0, sbMax.toInt()).toDouble()
        _sbEstado.value = progress
        _monto.value = pro
        filtro.monto = pro
    }


    fun comprobarCB(listaCB: List<CheckBox>) {
        listaCB.forEach { cb ->
            cb.setOnCheckedChangeListener { _, isChecked ->
                val estadoActual = _estado.value ?: HashMap()
                estadoActual[cb.text.toString()] = isChecked
                _estado.postValue(estadoActual)
                filtro.estado[cb.text.toString()] = isChecked
            }
        }
    }

    fun quitarCB(listaCB: List<CheckBox>){
        listaCB.forEach { cb ->
            cb.isChecked = false
        }
    }


    fun aplicarFiltro() {
        viewModelScope.launch {
            Log.d("Filtro", filtro.toString())
            _factModel.value =  getFiltradasUseCase(
                filtro.estado,
                filtro.monto,
                filtro.fechaMin,
                filtro.fechaMax
            )
        }
    }


    fun borrarFiltro() {
        _fechaMax.value = ""
        _fechaMin.value = ""
        _monto.value = 0.0
        _sbEstado.value = 0
        sbMax = 100.0
        filtro = Filtro()
        traerFacturas()
    }
}

