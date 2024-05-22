package com.example.iberdrola.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivitySignupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewmodel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signupXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onObserve()
        onListener()
    }


    private fun onObserve() {
        viewmodel.estado.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            if(it == "Se ha registrado correctamente."){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun onListener() {
        binding.btSignup.setOnClickListener{
            val email: String = binding.etUser.text.toString()
            val pass: String = binding.etPass.text.toString()
            val pass2: String = binding.etPass2.text.toString()

            if (email.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
            } else if(pass != pass2){
                Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show()
            } else{
                viewmodel.signUp(email,pass)
            }
        }

        binding.btCancel.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}