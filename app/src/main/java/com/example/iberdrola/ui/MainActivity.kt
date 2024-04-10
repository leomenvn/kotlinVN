package com.example.iberdrola.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityMainBinding
import com.example.iberdrola.ui.facturas.FacturasActivity
import com.example.iberdrola.ui.ss.SmartSolarActivity

class MainActivity : AppCompatActivity() {

    // Bindings
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

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
    }
}