package com.example.iberdrola.domain.usecases.auth

import com.google.firebase.auth.FirebaseAuth

class SignOutUseCase (private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    fun invoke() {
        auth.signOut()
    }

}