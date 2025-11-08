package com.example.tcm_app.ui.feature_login

import androidx.lifecycle.ViewModel
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class AdminLoginNavigation {
    object AdminDashboard : AdminLoginNavigation()
}

data class AdminLoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)

class AdminLoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminLoginState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<AdminLoginNavigation>()
    val navigationEvent = _navigationEvent

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onPasswordVisibilityToggle() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onAdminLoginClick() {
        if (uiState.value.email.equals("admin@tcm.mw", ignoreCase = true)) {
            _navigationEvent.tryEmit(AdminLoginNavigation.AdminDashboard)
        } else {
            // TODO: Handle incorrect admin credentials
        }
    }
}