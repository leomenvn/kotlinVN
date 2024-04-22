package com.example.iberdrola.ui.facturas


import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.MyApplication
import com.example.iberdrola.R

class FacturasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Mejora visual
        enableEdgeToEdge()
        setContentView(R.layout.activity_facturas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.facturasXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_facturas_lista, menu)
        return true
    }

}