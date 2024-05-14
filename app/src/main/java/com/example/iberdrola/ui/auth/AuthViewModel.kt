package com.example.iberdrola.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iberdrola.domain.usecases.auth.GetCurrentUserUseCase
import com.example.iberdrola.domain.usecases.auth.LogInUseCase
import com.example.iberdrola.domain.usecases.auth.ResetPassUseCase
import com.example.iberdrola.domain.usecases.auth.SignUpUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _estado = MutableLiveData<String>()
    val estado: LiveData<String>
        get() = _estado

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    private val logInUseCase = LogInUseCase()
    private val signUpUseCase = SignUpUseCase()
    private val resetPassUseCase = ResetPassUseCase()
    private val getCurrentUserUseCase = GetCurrentUserUseCase()

    init{
        _user.value = getCurrentUserUseCase.invoke()
    }

    fun logIn(email: String, pass: String){
        viewModelScope.launch {
            _estado.value = logInUseCase(email, pass)
            _user.value = getCurrentUserUseCase.invoke()
        }
    }

    fun signUp(email: String, pass: String) {
        viewModelScope.launch {
            _estado.value = signUpUseCase(email, pass)
        }
    }

    fun resetPass(email: String) {
        viewModelScope.launch {
            _estado.value = resetPassUseCase(email)
        }
    }
}
