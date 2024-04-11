package com.example.iberdrola.ui.facturas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasFiltroBinding
import com.example.iberdrola.databinding.FragmentFacturasListaBinding

class FacturasFiltroFragment : Fragment() {

    private lateinit var binding: FragmentFacturasFiltroBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFacturasFiltroBinding.inflate(layoutInflater)
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
    }


}