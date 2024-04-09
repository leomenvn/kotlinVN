package com.example.iberdrola.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth

        private lateinit var usuarioEt: EditText
        private lateinit var resetPasswordBtn: Button
        private lateinit var back: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_reset_password)

            auth = FirebaseAuth.getInstance()

            usuarioEt = findViewById(R.id.et_forgotpass)
    
            back = findViewById(R.id.bt_cancel)

            back.setOnClickListener {
                finish()
            }

            resetPasswordBtn.setOnClickListener {
                val usuario: String = usuarioEt.text.toString()
                if (TextUtils.isEmpty(usuario)) {
                    Toast.makeText(this, "Por favor, indique un usuario.", Toast.LENGTH_LONG).show()
                } else {
                    auth.sendPasswordResetEmail(usuario)
                        .addOnCompleteListener(this, OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Un enlace de recuperación ha sido enviado a su correo.", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(this, "No existe esa cuenta en nuestra Base de Datos.", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                }
            }
        }
}