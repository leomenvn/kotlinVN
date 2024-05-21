package com.example.iberdrola.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.core.RemoteConfigHelper
import com.example.iberdrola.data_ktor.KtorHelper
import com.example.iberdrola.domain.usecases.auth.GetCurrentUserUseCase
import com.example.iberdrola.domain.usecases.auth.SignOutUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private var remoteConfig: RemoteConfigHelper = RemoteConfigHelper.getInstance()

    private val _visibilidadLista = MutableLiveData<Boolean>()
    val visibilidadLista: LiveData<Boolean>
        get() = _visibilidadLista

    private val _modo = MutableLiveData<Boolean>()
    val modo: MutableLiveData<Boolean>
        get() = _modo

    private val _user = MutableLiveData<FirebaseUser>()
    val user: MutableLiveData<FirebaseUser>
        get() = _user


    private val getCurrentUser = GetCurrentUserUseCase()
    private val signOutUseCase = SignOutUseCase()

    init {
        _user.value = getCurrentUser.invoke()
        viewModelScope.launch {
            remoteConfig.fetch()
            _visibilidadLista.value = remoteConfig.getBoolean("listaVista")
            _modo.value = remoteConfig.getBoolean("temas")
        }
    }

    fun signOut() {
        signOutUseCase.invoke()
        _user.value = getCurrentUser.invoke()
    }

}

