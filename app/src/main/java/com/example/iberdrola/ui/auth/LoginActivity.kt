package com.example.iberdrola.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iberdrola.R
import com.example.iberdrola.ui.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usuarioEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var resetPasswordTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioEt = findViewById(R.id.et_NameLogin)
        passwordEt = findViewById(R.id.et_passLogin)

        signupBtn = findViewById(R.id.bt_Register)
        loginBtn = findViewById(R.id.bt_Login)

        resetPasswordTv = findViewById(R.id.tv_LostPass)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            val usuario: String = usuarioEt.text.toString()
            val password: String = passwordEt.text.toString()

            if(TextUtils.isEmpty(usuario) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
            } else{
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

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetPasswordTv.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}