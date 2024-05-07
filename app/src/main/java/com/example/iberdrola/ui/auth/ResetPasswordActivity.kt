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
import com.example.iberdrola.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private val viewmodel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reset_passwordXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onObserve()
        onListener()
    }


    private fun onObserve(){
        viewmodel.estadoReset.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }


    private fun onListener(){
        binding.btCancel.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btReset.setOnClickListener {
            val email = binding.etForgotpass.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Por favor, indique un usuario.", Toast.LENGTH_LONG).show()
            }else{
                viewmodel.resetPass(email)
            }
        }
    }
}