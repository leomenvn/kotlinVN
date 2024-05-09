package com.example.iberdrola.domain.usecases.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await

class LogInUseCase (private val auth: FirebaseAuth = FirebaseAuth.getInstance()){

    suspend operator fun invoke(email: String, pass: String): String {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
            "Bienvenido, $email"
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            "La dirección de correo electrónico o la contraseña son incorrectas."
        } catch (e: FirebaseAuthInvalidUserException) {
            when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "La dirección de correo electrónico no es válida."
                "ERROR_USER_DISABLED" -> "El usuario correspondiente al correo electrónico proporcionado ha sido deshabilitado."
                "ERROR_USER_NOT_FOUND" -> "No se encontró ningún usuario correspondiente al correo electrónico proporcionado."
                else -> "Se produjo un error inesperado: ${e.message}"
            }
        } catch (e: Exception) {
            "Se produjo un error inesperado: ${e.message}"
        }
    }
}