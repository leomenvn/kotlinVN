package com.example.iberdrola.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ResetPasswordActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private lateinit var binding: ActivityResetPasswordBinding


        override fun onCreate(savedInstanceState: Bundle?) {

            auth = FirebaseAuth.getInstance()
            binding = ActivityResetPasswordBinding.inflate(layoutInflater)

            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(binding.root)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reset_passwordXML)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }


            binding.btCancel.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            binding.btReset.setOnClickListener {
                resetPass()
            }
        }


    private fun resetPass() {
        val email: String = binding.etForgotpass.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor, indique un usuario.", Toast.LENGTH_LONG).show()
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Se ha enviado un correo electrónico para restablecer la contraseña", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        task.exception?.let { e ->
                            when (e) {
                                is FirebaseAuthInvalidUserException ->{
                                    Toast.makeText(this, "No se encontró ninguna cuenta asociada a este correo.", Toast.LENGTH_LONG).show()
                                }
                                is FirebaseAuthInvalidCredentialsException -> {
                                    Toast.makeText(this, "La dirección de correo electrónico no es válida.", Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    Toast.makeText(this, "Se produjo un error inesperado: " + e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                }
        }
    }
}