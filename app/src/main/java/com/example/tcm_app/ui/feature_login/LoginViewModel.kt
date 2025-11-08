package com.example.tcm_app.ui.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class LoginNavigation {
    object Dashboard : LoginNavigation()
    object AdminLogin : LoginNavigation()
    object Register : LoginNavigation()
    object TeacherVerification : LoginNavigation()
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<LoginNavigation>()
    val navigationEvent = _navigationEvent

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = "") }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = "") }
    }

    fun onPasswordVisibilityToggle() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        if (uiState.value.email.isBlank() || uiState.value.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please fill in all fields") }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }
            delay(1000) // Simulate network call
            _uiState.update { it.copy(isLoading = false) }
            _navigationEvent.tryEmit(LoginNavigation.Dashboard)
        }
    }

    fun onRegisterClick() {
        _navigationEvent.tryEmit(LoginNavigation.Register)
    }

    fun onAdminLoginClick() {
        _navigationEvent.tryEmit(LoginNavigation.AdminLogin)
    }
    
    fun onVerifyTeacherClick() {
        _navigationEvent.tryEmit(LoginNavigation.TeacherVerification)
    }
}