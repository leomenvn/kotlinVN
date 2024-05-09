package com.example.iberdrola.domain.usecases.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await

class ResetPassUseCase (private val auth: FirebaseAuth = FirebaseAuth.getInstance()){

    suspend operator fun invoke(email: String): String {
        return try {
            auth.sendPasswordResetEmail(email).await()
            "Se ha enviado un correo electrónico para restablecer la contraseña."
        } catch (e: FirebaseAuthInvalidUserException) {
            "No se encontró ninguna cuenta asociada a este correo."
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            "La dirección de correo electrónico no es válida."
        } catch (e: Exception) {
            "Se produjo un error inesperado: ${e.message}"
        }
    }
}