package com.example.tcm_app.ui.feature_cpd_tracking

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class CPDTrackingNavigation {
    object Profile : CPDTrackingNavigation()
    object Settings : CPDTrackingNavigation()
    object Login : CPDTrackingNavigation()
}

data class CPDActivity(
    val id: String,
    val title: String,
    val type: String,
    val provider: String,
    val date: String,
    val hours: Double,
    val points: Double,
    val status: String,
    val statusColor: Color
)

data class CPDTrackingState(
    val showAddDialog: Boolean = false,
    val selectedYear: Int = 2024,
    val totalPoints: Double = 15.0,
    val requiredPoints: Double = 20.0,
    val showMenu: Boolean = false,
    val cpdActivities: List<CPDActivity> = listOf(
        CPDActivity(
            "1",
            "Modern Teaching Methods Workshop",
            "Workshop",
            "Ministry of Education",
            "15 Oct 2024",
            16.0,
            5.0,
            "Approved",
            Color(0xFF43A047)
        ),
        CPDActivity(
            "2",
            "Digital Learning Tools Training",
            "Online Course",
            "UNESCO",
            "20 Sep 2024",
            20.0,
            6.0,
            "Approved",
            Color(0xFF43A047)
        ),
        CPDActivity(
            "3",
            "Classroom Management Seminar",
            "Seminar",
            "Education Hub",
            "5 Aug 2024",
            8.0,
            4.0,
            "Approved",
            Color(0xFF43A047)
        ),
        CPDActivity(
            "4",
            "STEM Education Conference",
            "Conference",
            "Malawi Science Society",
            "12 Nov 2024",
            12.0,
            0.0,
            "Pending",
            Color(0xFFFF9800)
        )
    )
) {
    val progress: Float = (totalPoints / requiredPoints).toFloat()
}

class CPDTrackingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CPDTrackingState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<CPDTrackingNavigation>()
    val navigationEvent = _navigationEvent

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
    }

    fun onProfileClick() {
        _navigationEvent.tryEmit(CPDTrackingNavigation.Profile)
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(CPDTrackingNavigation.Settings)
    }

    fun onLogoutClick() {
        _navigationEvent.tryEmit(CPDTrackingNavigation.Login)
    }

    fun onAddActivityClicked() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun onYearChanged(increment: Int) {
        _uiState.update { it.copy(selectedYear = it.selectedYear + increment) }
    }

    fun onAddActivity(title: String, type: String, hours: Double) {
        _uiState.update {
            val newActivity = CPDActivity(
                id = "${it.cpdActivities.size + 1}",
                title = title,
                type = type,
                provider = "Self-reported",
                date = "Today",
                hours = hours,
                points = 0.0,
                status = "Pending",
                statusColor = Color(0xFFFF9800)
            )
            it.copy(
                cpdActivities = it.cpdActivities + newActivity,
                showAddDialog = false
            )
        }
    }
}