package mw.gov.tcm.data.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(email: String, pass: String): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(email, pass).await()
    }

    override suspend fun signUp(email: String, pass: String): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
    }

    override suspend fun forgotPassword(email: String): Void? {
        return firebaseAuth.sendPasswordResetEmail(email).await()
    }
}