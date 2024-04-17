package com.example.iberdrola.ui.ss.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iberdrola.databinding.FragmentEnergiaBinding

class EnergiaFragment : Fragment() {

    private lateinit var binding: FragmentEnergiaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentEnergiaBinding.inflate(layoutInflater)

        return binding.root
    }

}