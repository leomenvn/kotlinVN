package com.example.iberdrola.ui.facturas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasListaBinding
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.ui.MainActivity
import com.example.iberdrola.ui.facturas.adapters.FacturasListaAdapter
import java.util.Date

class FacturasListaFragment : Fragment() {

    private lateinit var listaFacturas: List<Factura>
    private lateinit var rv: RecyclerView
    private lateinit var adapter: FacturasListaAdapter

    private val factListVM: FacturasListaViewModel by viewModels()
    private lateinit var binding: FragmentFacturasListaBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Binding
        binding = FragmentFacturasListaBinding.inflate(layoutInflater)

        listaFacturas = emptyList()
        adapter = FacturasListaAdapter(listaFacturas)


        // ViewModel
        factListVM.onCreate()
        factListVM.factModel.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.updateList(it)
            }
        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Invocacion de los botones
        onListener()

        rv = binding.facturasRV
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

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