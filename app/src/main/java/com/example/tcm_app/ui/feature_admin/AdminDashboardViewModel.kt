package com.example.tcm_app.ui.feature_admin

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class AdminDashboardNavigation {
    object Settings : AdminDashboardNavigation()
}

data class AdminDashboardState(
    val selectedTab: Int = 0,
    val showMenu: Boolean = false,
    val totalTeachers: Int = 1234,
    val pendingApplications: Int = 45,
    val activeApplications: Int = 78,
    val expiringSoon: Int = 23
)

class AdminDashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminDashboardState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<AdminDashboardNavigation>()
    val navigationEvent = _navigationEvent

    fun onTabSelected(tabIndex: Int) {
        _uiState.update { it.copy(selectedTab = tabIndex) }
    }

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(AdminDashboardNavigation.Settings)
    }
}