package com.example.tcm_app.ui.feature_profile

import androidx.lifecycle.ViewModel
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class ProfileNavigation {
    object Settings : ProfileNavigation()
    object Login : ProfileNavigation()
}

data class ProfileState(
    val showEditDialog: Boolean = false,
    val selectedSection: String = "",
    val showMenu: Boolean = false
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<ProfileNavigation>()
    val navigationEvent = _navigationEvent

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(ProfileNavigation.Settings)
    }

    fun onLogoutClick() {
        _navigationEvent.tryEmit(ProfileNavigation.Login)
    }

    fun onEditClicked() {
        _uiState.update { it.copy(showEditDialog = true) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(showEditDialog = false) }
    }

    fun onSectionSelected(section: String) {
        _uiState.update { it.copy(selectedSection = section, showEditDialog = true) }
    }
}