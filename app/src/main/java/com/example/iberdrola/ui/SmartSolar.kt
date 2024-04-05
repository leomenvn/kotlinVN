package com.example.iberdrola.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.iberdrola.R
import com.example.iberdrola.ui.fragments.Detalles
import com.example.iberdrola.ui.fragments.Energia
import com.example.iberdrola.ui.fragments.Instalacion
import com.google.android.material.tabs.TabLayout

class SmartSolar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_smart_solar)
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        // ViewPager
        val viewPager: ViewPager = findViewById(R.id.vpSM)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        // TabLayout
        val tabLayout: TabLayout = findViewById(R.id.tlSM)
        tabLayout.setupWithViewPager(viewPager)
    }
}

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Instalacion()
            1 -> Energia()
            2 -> Detalles()
            else -> throw IllegalArgumentException("No existe la opción: $position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Mi Instalacion"
            1 -> "Energia"
            2 -> "Detalles"
            else -> throw IllegalArgumentException("No existe la opción: $position")
        }
    }
}