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
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private val viewmodel: AuthViewModel by viewModel()

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
        viewmodel.estado.observe(this) {
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