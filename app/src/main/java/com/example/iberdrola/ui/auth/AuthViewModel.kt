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
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.iberdrola.MyApplication

class AuthViewModel: ViewModel() {

    private val _estado = MutableLiveData<String>()
    val estado: LiveData<String>
        get() = _estado

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _pass = MutableLiveData<String>()
    val pass: LiveData<String>
        get() = _pass

    private val _cb = MutableLiveData<Boolean>()
    val cb: LiveData<Boolean>
        get() = _cb

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    private val logInUseCase = LogInUseCase()
    private val signUpUseCase = SignUpUseCase()
    private val resetPassUseCase = ResetPassUseCase()
    private val getCurrentUserUseCase = GetCurrentUserUseCase()

    private var sharedPref: SharedPreferences
    private var editor: SharedPreferences.Editor


    init{
        sharedPref = criptoBuilder()
        editor = sharedPref.edit()
        if(sharedPref.getString("email", "") != ""){
            _email.value = sharedPref.getString("email", "")
            _pass.value = sharedPref.getString("pass", "")
            _cb.value = sharedPref.getBoolean("remember", false)
        }
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

    private fun criptoBuilder(): SharedPreferences {
        val masterKeyAlias = MasterKey.Builder(MyApplication.context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            MyApplication.context,
            "secure_login",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun guardarCred(email: String, pass: String, b: Boolean) {
        if (b) {
            editor.putString("email", email)
            editor.putString("pass", pass)
            editor.putBoolean("remember", true)
        } else {
            editor.clear()
            editor.putBoolean("remember", false)
        }
        editor.apply()
    }
}
