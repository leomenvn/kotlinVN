package com.example.iberdrola.ui.facturas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasListaBinding
import com.example.iberdrola.ui.MainActivity

class FacturasListaFragment : Fragment() {

    private lateinit var binding: FragmentFacturasListaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFacturasListaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMenuListener()
    }

    private fun onMenuListener() {
        // Vincular icono de menu con MaterialToolBar
        binding.mtbFacturas.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuFacturasLista -> {
                    val action = FacturasListaFragmentDirections.actionFacturasListaFragmentToFacturasFiltroFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        binding.mtbFacturas.setNavigationOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
}