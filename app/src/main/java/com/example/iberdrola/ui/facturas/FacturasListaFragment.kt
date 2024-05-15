package com.example.iberdrola.ui.facturas

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasListaBinding
import com.example.iberdrola.ui.MainActivity
import com.example.iberdrola.ui.facturas.adapters.FacturasListaAdapter

class FacturasListaFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: FacturasListaAdapter

    private val viewmodel: FacturasViewModel by activityViewModels()
    private lateinit var binding: FragmentFacturasListaBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFacturasListaBinding.inflate(layoutInflater)
        adapter = FacturasListaAdapter(emptyList()) { onItemSelected() }
        rv = binding.facturasRV
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        binding.switchLista.isChecked = viewmodel.retromock

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onObserve()
        onListener()
    }


    private fun onObserve(){
        viewmodel.factModel.observe(viewLifecycleOwner){
            if (it != null) {
                adapter.updateList(it)
            }
        }
    }


    private fun onListener() {
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

        binding.mtbFacturas.setNavigationOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.switchLista.setOnCheckedChangeListener { _, isChecked ->
            viewmodel.actualizarMock(isChecked)
        }
    }

    private fun onItemSelected() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.popup_facturas)

        val closeButton = dialog.findViewById<Button>(R.id.bt_popup)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}