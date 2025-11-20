package mw.gov.tcm.ui.feature_license_management

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.model.Application
import mw.gov.tcm.data.model.License
import mw.gov.tcm.data.model.Teacher
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import javax.inject.Inject

sealed class LicenseManagementNavigation {
    object Profile : LicenseManagementNavigation()
    object Settings : LicenseManagementNavigation()
    object Login : LicenseManagementNavigation()
    object ApplyForLicense : LicenseManagementNavigation()
}

data class LicenseManagementState(
    val teacher: Teacher? = null,
    val license: License? = null,
    val application: Application? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val showMenu: Boolean = false
)

@HiltViewModel
class LicenseManagementViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(LicenseManagementState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<LicenseManagementNavigation>()
    val navigationEvent = _navigationEvent

    init {
        fetchData()
    }

    private fun fetchData() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(error = "User not logged in", isLoading = false) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val teacher = teacherRepository.getTeacher(userId)
                val license = teacherRepository.getLicense(userId)
                val application = teacherRepository.getApplication(userId)
                _uiState.update { it.copy(teacher = teacher, license = license, application = application, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
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

    fun onApplyForLicenseClick() {
        if (_uiState.value.application == null) {
            _navigationEvent.tryEmit(LicenseManagementNavigation.ApplyForLicense)
        }
    }
}