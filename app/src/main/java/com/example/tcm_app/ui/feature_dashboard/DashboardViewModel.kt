package com.example.tcm_app.ui.feature_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DashboardNavigation {
    object Profile : DashboardNavigation()
    object Login : DashboardNavigation()
    object TeacherRegistration : DashboardNavigation()
    object LicenseManagement : DashboardNavigation()
    object CPDTracking : DashboardNavigation()
    object ApplicationStatus : DashboardNavigation()
    object Settings : DashboardNavigation()
    object TeacherVerification : DashboardNavigation()
    object Notifications : DashboardNavigation()
}

data class DashboardState(
    val userName: String = "John Banda",
    val registrationStatus: String = "Approved",
    val licenseStatus: String = "Active",
    val cpdPoints: Double = 15.0,
    val requiredCpdPoints: Double = 20.0,
    val showMenu: Boolean = false
)

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<DashboardNavigation>()
    val navigationEvent = _navigationEvent

    fun onMenuToggled() {
        _uiState.value = _uiState.value.copy(showMenu = !_uiState.value.showMenu)
    }

    fun onProfileClick() {
        _navigationEvent.tryEmit(DashboardNavigation.Profile)
    }

    fun onLogoutClick() {
        // TODO: Perform actual logout
        _navigationEvent.tryEmit(DashboardNavigation.Login)
    }

    fun onTeacherRegistrationClick() {
        _navigationEvent.tryEmit(DashboardNavigation.TeacherRegistration)
    }

    fun onLicenseManagementClick() {
        _navigationEvent.tryEmit(DashboardNavigation.LicenseManagement)
    }

    fun onCPDTrackingClick() {
        _navigationEvent.tryEmit(DashboardNavigation.CPDTracking)
    }

    fun onApplicationStatusClick() {
        _navigationEvent.tryEmit(DashboardNavigation.ApplicationStatus)
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(DashboardNavigation.Settings)
    }

    fun onTeacherVerificationClick() {
        _navigationEvent.tryEmit(DashboardNavigation.TeacherVerification)
    }

    fun onNotificationsClick() {
        _navigationEvent.tryEmit(DashboardNavigation.Notifications)
    }
}