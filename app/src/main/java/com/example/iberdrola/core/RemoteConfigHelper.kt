package com.example.iberdrola.core

import android.util.Log
import android.widget.Toast
import com.example.iberdrola.MyApplication.Companion.context
import com.example.iberdrola.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class RemoteConfigHelper private constructor(){

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun fetch() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Log Remote Config", "Config params updated")
                } else {
                    Toast.makeText(
                        context,
                        "Fallo durante el fetch: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
                }
            }
    }

    fun getBoolean(key: String): Boolean{
        return remoteConfig.getBoolean(key)
    }

    companion object {
        @Volatile
        private var instance: RemoteConfigHelper? = null

        fun getInstance(): RemoteConfigHelper {
            return instance ?: synchronized(this) {
                instance ?: RemoteConfigHelper().also { instance = it }
            }
        }
    }
}