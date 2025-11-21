package mw.gov.tcm.ui.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.auth.AuthRepository
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import javax.inject.Inject

sealed class TeacherForgotPasswordNavigation {
    object Back : TeacherForgotPasswordNavigation()
}

data class TeacherForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailSent: Boolean = false
)

@HiltViewModel
class TeacherForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherForgotPasswordState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<TeacherForgotPasswordNavigation>()
    val navigationEvent = _navigationEvent

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onForgotPasswordClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                authRepository.forgotPassword(uiState.value.email)
                _uiState.update { it.copy(isLoading = false, emailSent = true) }
                delay(3000)
                _navigationEvent.tryEmit(TeacherForgotPasswordNavigation.Back)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onBackClick() {
        _navigationEvent.tryEmit(TeacherForgotPasswordNavigation.Back)
    }
}