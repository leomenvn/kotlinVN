package com.example.iberdrola.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.R
import com.example.iberdrola.core.RemoteConfigHelper
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private var remoteConfig: RemoteConfigHelper = RemoteConfigHelper.getInstance()

    private val _visibilidadLista = MutableLiveData<Boolean>()
    val visibilidadLista: LiveData<Boolean>
        get() = _visibilidadLista

    private val _modo = MutableLiveData<Boolean>()
    val modo: MutableLiveData<Boolean>
        get() = _modo

    fun onCreate(){
        viewModelScope.launch{
            remoteConfig.fetch()
            _visibilidadLista.value = remoteConfig.getBoolean("listaVista")
            _modo.value = remoteConfig.getBoolean("temas")
        }
    }
}

