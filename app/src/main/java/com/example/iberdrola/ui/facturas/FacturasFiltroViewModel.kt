package com.example.iberdrola.ui.facturas

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Button
import androidx.lifecycle.ViewModel
import com.example.iberdrola.databinding.FragmentFacturasFiltroBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FacturasFiltroViewModel: ViewModel() {

    private lateinit var binding: FragmentFacturasFiltroBinding
    private val calendar = Calendar.getInstance(Locale.getDefault())

    private var FTfechaMin: String = "dia/mes/año"
    private var FTfechaMax: String = "dia/mes/año"
    private var FTestado: String = ""
    private var FTmonto: Double = 0.0


    fun onCreate() {
        binding.btFechaDesde.text = FTfechaMin
        binding.btFechaHasta.text = FTfechaMax
        binding.tvEstado.text = FTestado
        binding.tvImporte.text = FTmonto.toString()
    }

    fun escogerFecha(btFecha: Button, context: Context, mode: Boolean) {
        val datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            // Fecha escogida
            val fecha = Calendar.getInstance()
            fecha.set(year, month, dayOfMonth)

            // Formatear fecha para el botn y la BD
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            btFecha.text = formato.format(fecha.time)
            if(mode) FTfechaMin = btFecha.text.toString() else FTfechaMax = btFecha.text.toString()

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        // Restringir fecha máxima
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }

}