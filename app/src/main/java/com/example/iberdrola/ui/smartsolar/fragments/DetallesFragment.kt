package com.example.iberdrola.ui.smartsolar.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentDetallesBinding

class DetallesFragment : Fragment() {

    private lateinit var binding: FragmentDetallesBinding
    private val viewmodel: DetallesFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetallesBinding.inflate(layoutInflater)
        binding.etCau.isEnabled = false
        binding.etState.isEnabled = false
        binding.etTipo.isEnabled = false
        binding.etCompensacion.isEnabled = false
        binding.etPotencia.isEnabled = false

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onListener()
    }


    private fun onListener(){

        viewmodel.detalles.observe(viewLifecycleOwner){
            binding.etCau.setText(it.cau)
            binding.etState.setText(it.estado)
            binding.etTipo.setText(it.tipo)
            binding.etCompensacion.setText(it.compensacion)
            binding.etPotencia.setText(it.potencia)
        }

        binding.ivState.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.popup_detalles)

            val closeButton = dialog.findViewById<Button>(R.id.bt_popup)
            closeButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

}