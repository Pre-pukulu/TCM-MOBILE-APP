package mw.gov.tcm.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mw.gov.tcm.data.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun login(email: String, pass: String): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun signUp(email: String, pass: String): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}