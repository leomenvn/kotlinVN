package com.example.iberdrola.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityLoginBinding
import com.example.iberdrola.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    // Bindings
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)


        if(auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        // Mejora visual
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onListener()
    }


    private fun onListener() {
        // Llamada a la función de inicio de sesión
        binding.btLogin.setOnClickListener {
            logIn()
        }

        // Ir a la pantalla para registrarse
        binding.btRegister.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // Ir a la pantalla para recuperar contraseña
        binding.tvLostPass.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }


    // Función para iniciar sesión
    private fun logIn(){
        val email: String = binding.etNameLogin.text.toString()
        val password: String = binding.etPassLogin.text.toString()

        // Comprobar campos rellenos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Bienvenido, $email", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    task.exception?.let { e ->
                    when (e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(this, "La dirección de correo electrónico o la contraseña son incorrectas.", Toast.LENGTH_LONG).show()

                        } is FirebaseAuthInvalidUserException -> {
                            when (e.errorCode) {
                                "ERROR_INVALID_EMAIL" -> {
                                    Toast.makeText(this, "La dirección de correo electrónico no es válida.", Toast.LENGTH_LONG).show()
                                }
                                "ERROR_USER_DISABLED" -> {
                                    Toast.makeText(this, "El usuario correspondiente al correo electrónico proporcionado ha sido deshabilitado.", Toast.LENGTH_LONG).show()
                                }
                                "ERROR_USER_NOT_FOUND" -> {
                                    Toast.makeText(this, "No se encontró ningún usuario correspondiente al correo electrónico proporcionado.", Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    Toast.makeText(this, "Se produjo un error inesperado: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        else -> {
                            Toast.makeText(this, "Se produjo un error inesperado: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                    }
                }
            }
        }
    }
}