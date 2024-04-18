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
import com.example.iberdrola.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        binding = ActivitySignupBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signupXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btSignup.setOnClickListener{
            signUp()
        }

        binding.btCancel.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }



    private fun signUp() {
        val email: String = binding.etSignupNameLogin.text.toString()
        val password: String = binding.etSignupPassLogin.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Se ha registrado correctamente.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    task.exception?.let { e ->
                        when (e) {
                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(this, "La contraseña no es lo suficientemente segura.", Toast.LENGTH_LONG).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(this, "La dirección de correo electrónico no es válida.", Toast.LENGTH_LONG).show()
                            }
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(this, "Ya existe una cuenta con este correo electrónico.", Toast.LENGTH_LONG).show()
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