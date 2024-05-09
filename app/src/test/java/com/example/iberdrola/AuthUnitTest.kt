package com.example.iberdrola

import com.example.iberdrola.domain.usecases.auth.GetCurrentUserUseCase
import com.example.iberdrola.domain.usecases.auth.LogInUseCase
import com.example.iberdrola.domain.usecases.auth.ResetPassUseCase
import com.example.iberdrola.domain.usecases.auth.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/*
class AuthUnitTest {

    private val logInUseCase = LogInUseCase()
    private val signUpUseCase = SignUpUseCase()
    private val resetPassUseCase = ResetPassUseCase()
    private val signOutUseCase = LogInUseCase()
    private val getCurrentUserUseCase = GetCurrentUserUseCase()

    @Test
    suspend fun `given valid credentials, when invoke signInWithEmailAndPassword, then return welcome message`() =
            // Arrange
            val email = "admin@admin.com"
            val password = "admin123-"
            val expected = "Bienvenido, $email"

            // Act
            val actual = logInUseCase.invoke(email, password)

            // Assert
            assertEquals(expected, actual)

}*/