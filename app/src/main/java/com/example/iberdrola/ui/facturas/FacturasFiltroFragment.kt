package com.example.iberdrola.ui.facturas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasFiltroBinding
import java.util.Locale

class FacturasFiltroFragment : Fragment() {

    private val fechaDefault = "Día / Mes / Año"
    private lateinit var binding: FragmentFacturasFiltroBinding
    private lateinit var btFechaMin: Button
    private lateinit var btFechaMax: Button
    private lateinit var seekbarMonto: SeekBar
    private lateinit var tvMonto: TextView
    private lateinit var listaCB: List<CheckBox>

    private val viewmodel: FacturasViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFacturasFiltroBinding.inflate(layoutInflater)
        btFechaMin = binding.btFechaDesde
        btFechaMax = binding.btFechaHasta
        seekbarMonto = binding.sbImporte
        tvMonto = binding.tvRango
        listaCB = listOf(binding.cbPagadas, binding.cbAnuladas, binding.cbCuotafija, binding.cbPendientes, binding.cbPlanPago)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
        onListener()
    }

    private fun onObserve() {
        viewmodel.monto.observe(viewLifecycleOwner) { monto ->
            tvMonto.text = String.format(Locale.getDefault(), "%.2f", monto)
        }
        viewmodel.fechaMax.observe(viewLifecycleOwner){ fechaMax ->
            if(fechaMax == ""){
                btFechaMax.text = fechaDefault
            }else{
                btFechaMax.text = fechaMax
            }
        }
        viewmodel.fechaMin.observe(viewLifecycleOwner){ fechaMin ->
            if(fechaMin == ""){
                btFechaMin.text = fechaDefault
            }else{
                btFechaMin.text = fechaMin
            }
        }
        viewmodel.estado.observe(viewLifecycleOwner){ estado ->
            listaCB.forEach { cb ->
                cb.isChecked = estado[cb.text.toString()] ?: false
            }
        }
        viewmodel.sbEstado.observe(viewLifecycleOwner){ sbEstado ->
            seekbarMonto.progress = sbEstado
        }
    }

    private fun onListener() {
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
            viewmodel.escogerFecha(requireContext(), true)
        }

        btFechaMax.setOnClickListener{
            viewmodel.escogerFecha(requireContext(), false)
        }


        // Seekbar
        seekbarMonto.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewmodel.escogerMonto(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // CheckBoxes
        viewmodel.comprobarCB(listaCB)

        // Aplicar filtro
        binding.btAplicar.setOnClickListener {
            viewmodel.aplicarFiltro()
        }

        // Aplicar filtro
        binding.btCancelar.setOnClickListener {
            viewmodel.borrarFiltro()
            viewmodel.quitarCB(listaCB)
        }
    }

}