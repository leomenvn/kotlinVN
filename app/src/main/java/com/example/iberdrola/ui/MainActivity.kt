package com.example.iberdrola.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.core.RemoteConfigHelper
import com.example.iberdrola.databinding.ActivityMainBinding
import com.example.iberdrola.ui.auth.LoginActivity
import com.example.iberdrola.ui.facturas.FacturasActivity
import com.example.iberdrola.ui.navegacion.NavegacionActivity
import com.example.iberdrola.ui.ss.SmartSolarActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class MainActivity : AppCompatActivity() {

    // Bindings
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        remoteConfig = RemoteConfigHelper().getRemoteConfig()

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("Remote", "Config params updated: $updated")
                    Log.d("Remote", remoteConfig.getBoolean("listaVista").toString())
                    Log.d("Remote", remoteConfig.getBoolean("temas").toString())
                    binding.swVerLista.isChecked = remoteConfig.getBoolean("listaVista")
                    binding.swTema.isChecked = remoteConfig.getBoolean("temas")
                } else {
                    Toast.makeText(
                        this,
                        "FALLO DURANTE EL FETCH",
                        Toast.LENGTH_SHORT,
                    ).show()
                    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
                }
            }

        if(binding.swTema.isChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        // Mejora visual
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