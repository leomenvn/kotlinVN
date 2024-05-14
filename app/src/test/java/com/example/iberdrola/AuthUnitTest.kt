package com.example.iberdrola

import com.example.iberdrola.domain.usecases.auth.GetCurrentUserUseCase
import com.example.iberdrola.domain.usecases.auth.LogInUseCase
import com.example.iberdrola.domain.usecases.auth.ResetPassUseCase
import com.example.iberdrola.domain.usecases.auth.SignOutUseCase
import com.example.iberdrola.domain.usecases.auth.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class AuthUnitTest {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logInUseCase: LogInUseCase
    private lateinit var resetPassUseCase: ResetPassUseCase
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var signOutUseCase: SignOutUseCase
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @Before
    fun setup() {
    }


    @Test
    fun loginCuandoCredencialesCorrectas() = runBlocking {
        firebaseAuth = mock(FirebaseAuth::class.java)
        logInUseCase = LogInUseCase(firebaseAuth)

        val email = "admin@admin.com"
        val password = "admin123-"
        val expected = "Bienvenido, $email"

        `when`(logInUseCase(email, password)).thenReturn(expected)

        val result = logInUseCase(email, password)

        assertEquals(expected, result)
    }


}
