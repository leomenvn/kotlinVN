package com.example.iberdrola.ui.auth

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

class AuthViewModel: ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _usuario = MutableLiveData<FirebaseUser>()
    val usuario: LiveData<FirebaseUser>
        get() = _usuario

    private val _estadoLog = MutableLiveData<String>()
    val estadoLog: LiveData<String>
        get() = _estadoLog

    private val _estadoSign = MutableLiveData<String>()
    val estadoSign: LiveData<String>
        get() = _estadoSign

    private val _estadoReset = MutableLiveData<String>()
    val estadoReset: LiveData<String>
        get() = _estadoReset


    init {
        _usuario.value = auth.currentUser
    }

    
    fun logIn(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _estadoLog.value = "Bienvenido, $email"
                _usuario.value = auth.currentUser
            } else {
                task.exception?.let { e ->
                    _estadoLog.value = when (e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            "La dirección de correo electrónico o la contraseña son incorrectas."

                            } is FirebaseAuthInvalidUserException -> {
                            when (e.errorCode) {
                                "ERROR_INVALID_EMAIL" -> {
                                    "La dirección de correo electrónico no es válida."
                                }

                                "ERROR_USER_DISABLED" -> {
                                    "El usuario correspondiente al correo electrónico proporcionado ha sido deshabilitado."
                                }

                                "ERROR_USER_NOT_FOUND" -> {
                                    "No se encontró ningún usuario correspondiente al correo electrónico proporcionado."
                                }

                                else -> {
                                    "Se produjo un error inesperado: ${e.message}"
                                }
                            }
                        } else -> {
                        "Se produjo un error inesperado: ${e.message}"
                        }
                    }
                }
            }
        }
    }


    fun signUp(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _estadoSign.value = "Se ha registrado correctamente."
            } else {
                task.exception?.let { e ->
                    _estadoSign.value = when (e) {
                        is FirebaseAuthWeakPasswordException -> {
                            "La contraseña no es lo suficientemente segura."
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            "La dirección de correo electrónico no es válida."
                        }
                        is FirebaseAuthUserCollisionException -> {
                            "Ya existe una cuenta con este correo electrónico."
                        }
                        else -> {
                            "Se produjo un error inesperado: " + e.message
                        }
                    }
                }
            }
        }
    }


    fun resetPass(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _estadoReset.value = "Se ha enviado un correo electrónico para restablecer la contraseña."
            } else {
                task.exception?.let { e ->
                    _estadoReset.value = when (e) {
                        is FirebaseAuthInvalidUserException ->{
                            "No se encontró ninguna cuenta asociada a este correo."
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            "La dirección de correo electrónico no es válida."
                        }
                        else -> {
                            "Se produjo un error inesperado: " + e.message
                        }
                    }
                }
            }
        }
    }
}
