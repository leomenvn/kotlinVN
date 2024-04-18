package com.example.iberdrola.ui.facturas

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasFiltroBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FacturasFiltroFragment : Fragment() {

    private lateinit var binding: FragmentFacturasFiltroBinding
    private val calendar = Calendar.getInstance()
    private lateinit var btFechaMin: Button
    private lateinit var btFechaMax: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFacturasFiltroBinding.inflate(layoutInflater)
        btFechaMin = binding.btFechaDesde
        btFechaMax = binding.btFechaHasta

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

        btFechaMin.setOnClickListener{
            escogerFecha(btFechaMin)
        }

        btFechaMax.setOnClickListener{
            escogerFecha(btFechaMax)
        }
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

}