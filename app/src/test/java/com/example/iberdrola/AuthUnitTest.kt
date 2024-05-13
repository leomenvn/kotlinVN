import com.example.iberdrola.domain.usecases.auth.GetCurrentUserUseCase
import com.example.iberdrola.domain.usecases.auth.LogInUseCase
import com.example.iberdrola.domain.usecases.auth.ResetPassUseCase
import com.example.iberdrola.domain.usecases.auth.SignOutUseCase
import com.example.iberdrola.domain.usecases.auth.SignUpUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class AuthUnitTest {
/*
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logInUseCase: LogInUseCase
    private lateinit var resetPassUseCase: ResetPassUseCase
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var signOutUseCase: SignOutUseCase
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @Before
    fun setup() {
        firebaseAuth = mock(FirebaseAuth::class.java)

    }


    @Test
    fun loginCuandoCredencialesCorrectas(): Unit = runBlocking {
        logInUseCase = LogInUseCase(firebaseAuth)

        val email = "ulmenesesg@gmail.com"
        val password = "admin123-"
        val expected = "Bienvenido, $email"

        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(expected)

        val result = logInUseCase(email, password)

        assertEquals(expected, result)
    }


    @Test
    fun loginCuandoNoExisteUsuario(){

    }


    @Test
    fun loginCuandoContresenaIncorrecta(){

    }


    @Test
    fun loginCuandoUsuarioBloqueado(){

    }*/

}
