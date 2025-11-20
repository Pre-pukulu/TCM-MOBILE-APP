package mw.gov.tcm.ui.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.auth.AuthRepository
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import javax.inject.Inject

sealed class LoginNavigation {
    object Dashboard : LoginNavigation()
    object ForgotPassword : LoginNavigation()
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<LoginNavigation>()
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

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                authRepository.login(uiState.value.email, uiState.value.password)
                _uiState.update { it.copy(isLoading = false) }
                _navigationEvent.tryEmit(LoginNavigation.Dashboard)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onForgotPasswordClick() {
        _navigationEvent.tryEmit(LoginNavigation.ForgotPassword)
    }
}