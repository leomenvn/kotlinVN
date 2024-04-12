package com.example.iberdrola.ui.facturas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasListaBinding
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.ui.MainActivity
import com.example.iberdrola.ui.facturas.adapters.FacturasListaAdapter
import java.util.Date

class FacturasListaFragment : Fragment() {

    private lateinit var listaFacturas: List<Factura>

    private lateinit var binding: FragmentFacturasListaBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFacturasListaBinding.inflate(layoutInflater)

        listaFacturas = listOf(
            Factura(1, "Factura 1", 23.00, Date(), Date(), true),
            Factura(2, "Factura 2", 45.00, Date(), Date(), true),
            Factura(3, "Factura 3", 56.00, Date(), Date(), false),
            Factura(4, "Factura 4", 77.00, Date(), Date(), true),
            Factura(5, "Factura 5", 88.00, Date(), Date(), false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Invocacion de los botones
        onListener()

        val rv = binding.facturasRV
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = FacturasListaAdapter(listaFacturas)
    }


    // Método auxiliar para vincular elementos de la toolbar
    private fun onListener() {
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