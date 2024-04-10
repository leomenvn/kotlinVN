package com.example.iberdrola.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityLoginBinding
import com.example.iberdrola.databinding.ActivityMainBinding
import com.example.iberdrola.ui.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    // Bindings
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Mejora visual
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Llamada a la función de inicio de sesión
        binding.btLogin.setOnClickListener {
            logIn()
        }

        // Ir a la pantalla para registrarse
        binding.btRegister.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Ir a la pantalla para recuperar contraseña
        binding.tvLostPass.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para iniciar sesión
    private fun logIn(){
        val usuario: String = binding.etNameLogin.text.toString()
        val password: String = binding.etPassLogin.text.toString()

        // Comprobar campos rellenos
        if(TextUtils.isEmpty(usuario) || TextUtils.isEmpty(password)) {
            //Toast.makeText(this@LoginActivity, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        else{
            auth.signInWithEmailAndPassword(usuario, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Bienvenido, $usuario", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this, "ERROR: no se pudo iniciar sesión.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}