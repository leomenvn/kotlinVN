package com.example.iberdrola.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityMainBinding
import com.example.iberdrola.ui.auth.LoginActivity
import com.example.iberdrola.ui.facturas.FacturasActivity
import com.example.iberdrola.ui.navegacion.NavegacionActivity
import com.example.iberdrola.ui.smartsolar.SmartSolarActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewmodel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onListener()
    }


    private fun onListener() {

        binding.ivPractica1.setOnClickListener{
            val intent = Intent(this, FacturasActivity::class.java)
            startActivity(intent)
        }

        binding.ivPractica2.setOnClickListener{
            val intent = Intent(this, SmartSolarActivity::class.java)
            startActivity(intent)
        }

        binding.ivNav.setOnClickListener{
            val intent = Intent(this, NavegacionActivity::class.java)
            startActivity(intent)
        }

        binding.btCerrarSesion.setOnClickListener{
            viewmodel.signOut()
        }

        viewmodel.visibilidadLista.observe(this){ aux ->
            binding.swVerLista.isChecked = aux
        }

        viewmodel.modo.observe(this){ modo ->
            if(modo){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swTema.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swTema.isChecked = false
            }
        }

        viewmodel.user.observe(this){
            if(it == null){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                val text = "Bievenido, "+ it.email
                binding.tvBienvenido.text = text
            }
        }
    }
}