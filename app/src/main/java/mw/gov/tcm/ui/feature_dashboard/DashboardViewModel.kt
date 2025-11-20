package mw.gov.tcm.ui.feature_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.model.Teacher
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import javax.inject.Inject


sealed class DashboardNavigation {
    object Profile : DashboardNavigation()
    object Settings : DashboardNavigation()
    object Login : DashboardNavigation()
}

data class DashboardState(
    val teacher: Teacher? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showMenu: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<DashboardNavigation>()
    val navigationEvent = _navigationEvent

    init {
        getTeacher()
    }

    private fun getTeacher() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(error = "User not logged in") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val teacher = teacherRepository.getTeacher(userId)
                _uiState.update { it.copy(isLoading = false, teacher = teacher) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
    }

    fun onProfileClick() {
        _navigationEvent.tryEmit(DashboardNavigation.Profile)
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(DashboardNavigation.Settings)
    }

    fun onLogoutClick() {
        firebaseAuth.signOut()
        _navigationEvent.tryEmit(DashboardNavigation.Login)
    }
}