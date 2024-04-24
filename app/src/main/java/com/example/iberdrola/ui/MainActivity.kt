package com.example.iberdrola.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityMainBinding
import com.example.iberdrola.ui.auth.LoginActivity
import com.example.iberdrola.ui.facturas.FacturasActivity
import com.example.iberdrola.ui.navegacion.NavegacionActivity
import com.example.iberdrola.ui.ss.SmartSolarActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Bindings
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        // Mejora visual
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onListener()
    }


    private fun onListener() {
        binding.tvBienvenido.text = "Bievenido, ${auth.currentUser?.email}"

        // La flecha lleva a la práctica 1
        binding.ivPractica1.setOnClickListener{
            val intent = Intent(this, FacturasActivity::class.java)
            startActivity(intent)
        }

        // La flecha lleva a la práctica 2
        binding.ivPractica2.setOnClickListener{
            val intent = Intent(this, SmartSolarActivity::class.java)
            startActivity(intent)
        }

        // La flecha lleva a Navegacion
        binding.ivNav.setOnClickListener{
            val intent = Intent(this, NavegacionActivity::class.java)
            startActivity(intent)
        }

        // Cerrar sesión (volver a login)
        binding.btCerrarSesion.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}