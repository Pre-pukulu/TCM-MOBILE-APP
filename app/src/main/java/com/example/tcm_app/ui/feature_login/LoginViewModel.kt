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
    object Register : LoginNavigation()
    object TeacherVerification : LoginNavigation()
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<LoginNavigation>()
    val navigationEvent = _navigationEvent

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onPasswordVisibilityToggle() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        if (_uiState.value.email.isBlank() || _uiState.value.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please fill in all fields") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000) // Mock login delay
            _navigationEvent.tryEmit(LoginNavigation.Dashboard)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _navigationEvent.tryEmit(LoginNavigation.Register)
        }
    }

    fun onTeacherVerificationClick() {
        viewModelScope.launch {
            _navigationEvent.tryEmit(LoginNavigation.TeacherVerification)
        }
    }
}