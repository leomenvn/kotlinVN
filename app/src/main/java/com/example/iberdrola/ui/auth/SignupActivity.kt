package com.example.iberdrola.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewmodel: AuthViewModel by viewModels()

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
            val email: String = binding.etSignupNameLogin.text.toString()
            val pass: String = binding.etSignupPassLogin.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
            } else {
                viewmodel.signUp(email,pass)
            }
        }

        binding.btCancel.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}