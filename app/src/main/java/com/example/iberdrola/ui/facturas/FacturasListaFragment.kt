package com.example.iberdrola.ui.facturas

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentFacturasListaBinding
import com.example.iberdrola.ui.MainActivity
import com.example.iberdrola.ui.facturas.adapters.FacturasListaAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FacturasListaFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: FacturasListaAdapter

    private val viewmodel: FacturasViewModel by activityViewModel()
    private lateinit var binding: FragmentFacturasListaBinding
    private lateinit var selector: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFacturasListaBinding.inflate(layoutInflater)
        adapter = FacturasListaAdapter(emptyList()) { onItemSelected() }
        rv = binding.facturasRV
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        selector = binding.selector

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.retrofit -> {
                    selector.text = "RETROFIT"
                    viewmodel.setTipo(1)
                    true
                }
                R.id.retromock -> {
                    selector.text = "RETROMOCK"
                    viewmodel.setTipo(2)
                    true
                }
                R.id.ktor -> {
                    selector.text = "KTOR"
                    viewmodel.setTipo(3)
                    true
                }
                else -> {
                    Log.d("POPUP", it.itemId.toString())
                    false
                }
            }
        }
        popup.show()
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
        selector.setOnClickListener { v: View ->
            showMenu(v, R.menu.menu_lista_opcion)
        }

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