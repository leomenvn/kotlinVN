package com.example.iberdrola.domain.usecases.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class SignUpUseCase (private val auth: FirebaseAuth = FirebaseAuth.getInstance()){

    suspend operator fun invoke(email:String, pass:String): String{
        return try {
            auth.createUserWithEmailAndPassword(email, pass).await()
            "Se ha registrado correctamente."
        } catch(e: FirebaseAuthWeakPasswordException) {
            "La contraseña no es lo suficientemente segura."
        }catch(e: FirebaseAuthInvalidCredentialsException) {
            "La dirección de correo electrónico no es válida."
        }catch(e: FirebaseAuthUserCollisionException) {
            "Ya existe una cuenta con este correo electrónico."
        }catch(e: Exception) {
            "Se produjo un error inesperado: " + e.message
        }
    }
}