package com.example.iberdrola.domain.usecases.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GetCurrentUserUseCase (private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

        operator fun invoke(): FirebaseUser? {
            return auth.currentUser
        }
}