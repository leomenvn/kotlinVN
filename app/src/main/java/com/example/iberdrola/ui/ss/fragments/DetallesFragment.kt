package com.example.iberdrola.ui.ss.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.iberdrola.R
import com.example.iberdrola.databinding.FragmentDetallesBinding

class DetallesFragment : Fragment() {

    // Bindings
    private lateinit var binding: FragmentDetallesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetallesBinding.inflate(layoutInflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etCau.isEnabled = false
        binding.etState.isEnabled = false
        binding.etTipo.isEnabled = false
        binding.etCompensacion.isEnabled = false
        binding.etPotencia.isEnabled = false

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