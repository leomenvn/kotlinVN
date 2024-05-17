package com.example.iberdrola.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityLoginBinding
import com.example.iberdrola.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewmodel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginXML)) { v, insets ->
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
        }

        viewmodel.user.observe(this) {
            if(it != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        viewmodel.email.observe(this) {
            binding.etNameLogin.setText(it)
        }

        viewmodel.pass.observe(this){
            binding.etPassLogin.setText(it)
        }

        viewmodel.cb.observe(this){
            binding.cbRecordar.isChecked = it
        }
    }


    private fun onListener() {
        binding.btLogin.setOnClickListener {
            val email = binding.etNameLogin.text.toString()
            val pass = binding.etPassLogin.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Por favor, rellene los campos obligatorios.", Toast.LENGTH_LONG).show()
            }else{
                viewmodel.guardarCred(email, pass, binding.cbRecordar.isChecked)
                viewmodel.logIn(email, pass)
            }
        }

        binding.btRegister.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.tvLostPass.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

}