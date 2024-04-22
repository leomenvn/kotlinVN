package com.example.iberdrola.ui.facturas

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasFiltroBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FacturasFiltroFragment : Fragment() {

    private lateinit var binding: FragmentFacturasFiltroBinding
    private val calendar = Calendar.getInstance(Locale("es", "ES"))
    private lateinit var btFechaMin: Button
    private lateinit var btFechaMax: Button
    private lateinit var seekbarMonto: SeekBar
    private lateinit var tvMonto: TextView

    private lateinit var listaCB: List<CheckBox>
    private lateinit var cbPagadas: CheckBox
    private lateinit var cbAnuladas: CheckBox
    private lateinit var cbCuota: CheckBox
    private lateinit var cbPendientes: CheckBox
    private lateinit var cbPlan: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFacturasFiltroBinding.inflate(layoutInflater)
        btFechaMin = binding.btFechaDesde
        btFechaMax = binding.btFechaHasta
        seekbarMonto = binding.sbImporte
        tvMonto = binding.tvRango
        cbPagadas = binding.cbPagadas
        cbAnuladas = binding.cbAnuladas
        cbCuota = binding.cbCuotafija
        cbPendientes = binding.cbPendientes
        cbPlan = binding.cbPlanPago
        listaCB = listOf(cbPagadas, cbAnuladas, cbCuota, cbPendientes, cbPlan)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onClickListener()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun onClickListener() {
        // Vincular icono de menu con MaterialToolBar
        binding.mtbFactFilt.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuFacturasFiltro -> {
                    val action = FacturasFiltroFragmentDirections.actionFacturasFiltroFragmentToFacturasListaFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        // Botones fechas
        btFechaMin.setOnClickListener{
            escogerFecha(btFechaMin)
        }

        btFechaMax.setOnClickListener{
            escogerFecha(btFechaMax)
        }

        // Checkboxes
        comprobarCB()

        // Seekbar
        seekbarMonto.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val monto = progress.toFloat() * 2.5 //

                val limitedAmount = when {
                    monto < 5 -> 5f
                    monto > 250 -> 250f
                    else -> monto
                }
                tvMonto.text = String.format(Locale.getDefault(), "%.2f", limitedAmount)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }


    private fun escogerFecha(btFecha: Button) {
        val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            // Fecha escogida
            val fecha = Calendar.getInstance()
            fecha.set(year, month, dayOfMonth)

            // Formatear fecha para el boton
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(fecha.time)
            btFecha.text = formattedDate
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        // Restringit fecha máxima
        datePicker.datePicker.maxDate = System.currentTimeMillis()

        datePicker.show()
    }

    private fun comprobarCB(){
        listaCB.forEach { cb ->
            cb.setOnClickListener {
                listaCB.filter { it != cb }.forEach { it.isChecked = false }
            }
        }
    }

}