package com.example.iberdrola.ui.navegacion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ActivityNavegacionBinding
import com.example.iberdrola.ui.MainActivity

class NavegacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavegacionBinding
    private lateinit var webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavegacionBinding.inflate(layoutInflater)
        webview = binding.wbNav

        // Mejora visual
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.navegacionXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onListener()
    }

    private fun onListener() {
        binding.mtbNav.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.menuNavegacion -> {
                    webview.loadUrl("about:blank")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }

        }

        binding.btNavegador.setOnClickListener{
            webview.loadUrl("about:blank")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iberdrola.es/"))
            startActivity(intent)

        }

        binding.btWebview.setOnClickListener {
            webview.loadUrl("https://www.iberdrola.es/")
        }
    }
}