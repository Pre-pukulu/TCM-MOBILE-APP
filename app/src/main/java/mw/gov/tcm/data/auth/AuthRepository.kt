package mw.gov.tcm.data.auth

import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun login(email: String, pass: String): AuthResult
    suspend fun signUp(email: String, pass: String): AuthResult
    suspend fun forgotPassword(email: String): Void?
}