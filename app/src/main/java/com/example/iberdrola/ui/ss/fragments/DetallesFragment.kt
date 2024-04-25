package com.example.iberdrola.ui.ss.fragments

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

    // Bindings
    private lateinit var binding: FragmentDetallesBinding
    private val viewmodel: DetallesFragmentViewModel by viewModels()

    private lateinit var etCau: EditText
    private lateinit var etState: EditText
    private lateinit var etTipo: EditText
    private lateinit var etCompensacion: EditText
    private lateinit var etPotencia: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetallesBinding.inflate(layoutInflater)
        etCau = binding.etCau
        etState = binding.etState
        etTipo = binding.etTipo
        etCompensacion = binding.etCompensacion
        etPotencia = binding.etPotencia

        etCau.isEnabled = false
        etState.isEnabled = false
        etTipo.isEnabled = false
        etCompensacion.isEnabled = false
        etPotencia.isEnabled = false

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.actualizarDetalles(etCau, etState, etTipo, etCompensacion, etPotencia)
        onListener()
    }

    private fun onListener(){
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