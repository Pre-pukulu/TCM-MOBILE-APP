package com.example.tcm_app.ui.feature_license_management

import androidx.lifecycle.ViewModel
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class LicenseManagementNavigation {
    object Profile : LicenseManagementNavigation()
    object Settings : LicenseManagementNavigation()
    object Login : LicenseManagementNavigation()
}

data class LicenseManagementState(
    val licenseNumber: String = "TCM/2024/001234",
    val teacherName: String = "John Banda",
    val issueDate: String = "15 Jan 2024",
    val expiryDate: String = "14 Jan 2026",
    val daysToExpiry: Int = 425,
    val status: String = "Active",
    val showMenu: Boolean = false
)

class LicenseManagementViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LicenseManagementState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<LicenseManagementNavigation>()
    val navigationEvent = _navigationEvent

    fun onMenuToggled() {
        _uiState.value = _uiState.value.copy(showMenu = !_uiState.value.showMenu)
    }

    fun onProfileClick() {
        _navigationEvent.tryEmit(LicenseManagementNavigation.Profile)
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(LicenseManagementNavigation.Settings)
    }

    fun onLogoutClick() {
        _navigationEvent.tryEmit(LicenseManagementNavigation.Login)
    }
}