package com.example.iberdrola.ui.facturas

import android.annotation.SuppressLint
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
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.util.Locale

class FacturasFiltroFragment : Fragment() {

    private val fechaDefault = "Día / Mes / Año"
    private lateinit var binding: FragmentFacturasFiltroBinding
    private lateinit var btFechaMin: Button
    private lateinit var btFechaMax: Button
    private lateinit var seekbarMonto: SeekBar
    private lateinit var tvMonto: TextView
    private lateinit var listaCB: List<CheckBox>

    private val viewmodel: FacturasViewModel by activityViewModel()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFacturasFiltroBinding.inflate(layoutInflater)
        btFechaMin = binding.btFechaDesde
        btFechaMax = binding.btFechaHasta
        seekbarMonto = binding.sbImporte
        tvMonto = binding.tvRango
        listaCB = listOf(binding.cbPagadas, binding.cbAnuladas, binding.cbCuotafija, binding.cbPendientes, binding.cbPlanPago)
        binding.tvRangoMAX.text = String.format("%.1f", viewmodel.sbMax)+"\u20AC"

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
        viewmodel.estado.observe(viewLifecycleOwner){
            listaCB.forEach { cb ->
                cb.isChecked = it[cb.text.toString()] ?: false
            }
        }
        viewmodel.sbEstado.observe(viewLifecycleOwner){
            seekbarMonto.progress = it
        }
    }

    private fun onListener() {

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
            viewmodel.escogerFecha(true)
        }

        btFechaMax.setOnClickListener{
            viewmodel.escogerFecha(false)
        }


        seekbarMonto.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewmodel.escogerMonto(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        viewmodel.comprobarCB(listaCB)


        binding.btAplicar.setOnClickListener {
            viewmodel.aplicarFiltro()
            val action = FacturasFiltroFragmentDirections.actionFacturasFiltroFragmentToFacturasListaFragment()
            findNavController().navigate(action)
        }

        binding.btCancelar.setOnClickListener {
            viewmodel.borrarFiltro()
            viewmodel.quitarCB(listaCB)
        }
    }

}