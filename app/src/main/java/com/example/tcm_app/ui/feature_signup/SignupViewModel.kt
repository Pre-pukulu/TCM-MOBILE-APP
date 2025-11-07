package com.example.tcm_app.ui.feature_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class SignupNavigation {
    object Dashboard : SignupNavigation()
    object Back : SignupNavigation()
}

data class SignupState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val hasAgreedToTerms: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<SignupNavigation>()
    val navigationEvent = _navigationEvent

    fun onFirstNameChange(firstName: String) {
        _uiState.update { it.copy(firstName = firstName, errorMessage = null) }
    }

    fun onLastNameChange(lastName: String) {
        _uiState.update { it.copy(lastName = lastName, errorMessage = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPhoneChange(phone: String) {
        _uiState.update { it.copy(phone = phone, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, errorMessage = null) }
    }

    fun onPasswordVisibilityToggle() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onConfirmPasswordVisibilityToggle() {
        _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun onAgreeToTermsToggle(agreed: Boolean) {
        _uiState.update { it.copy(hasAgreedToTerms = agreed, errorMessage = null) }
    }

    fun onSignupClick() {
        val state = _uiState.value
        when {
            state.firstName.isBlank() || state.lastName.isBlank() || state.email.isBlank() ||
            state.phone.isBlank() || state.password.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Please fill in all fields") }
            }
            state.password != state.confirmPassword -> {
                _uiState.update { it.copy(errorMessage = "Passwords do not match") }
            }
            state.password.length < 6 -> {
                _uiState.update { it.copy(errorMessage = "Password must be at least 6 characters") }
            }
            !state.hasAgreedToTerms -> {
                _uiState.update { it.copy(errorMessage = "Please agree to the terms and conditions") }
            }
            else -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    // Mock registration - navigate to dashboard
                    _navigationEvent.tryEmit(SignupNavigation.Dashboard)
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onBackClick() {
        _navigationEvent.tryEmit(SignupNavigation.Back)
    }
}