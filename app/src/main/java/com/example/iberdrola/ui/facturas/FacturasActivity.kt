package com.example.iberdrola.ui.facturas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityFacturasBinding
import com.example.iberdrola.databinding.ActivityMainBinding
import com.example.iberdrola.ui.MainActivity
import com.google.gson.Gson

class FacturasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFacturasBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityFacturasBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        // Mejora visual
        enableEdgeToEdge()
        setContentView(R.layout.activity_facturas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.facturasXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Volver al MainActivity
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var gson = Gson()
        return when (item.itemId) {
            R.id.menuFacturas -> {
                val miIntent = Intent(this, FacturasFiltroActivity::class.java)
                startActivity(miIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}