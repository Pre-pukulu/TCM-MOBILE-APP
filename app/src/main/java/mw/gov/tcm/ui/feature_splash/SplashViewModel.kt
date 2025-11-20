package mw.gov.tcm.ui.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import mw.gov.tcm.data.Result
import mw.gov.tcm.data.settings.RegistrationProgressRepository
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import javax.inject.Inject

sealed class SplashNavigation {
    object Login : SplashNavigation()
    object Dashboard : SplashNavigation()
    object TeacherRegistration : SplashNavigation()
    data class TeacherRegistrationInProgress(val step: Int) : SplashNavigation()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val teacherRepository: TeacherRepository,
    private val registrationProgressRepository: RegistrationProgressRepository
) : ViewModel() {

    private val _navigationEvent = mutableSingleFireNavigation<SplashNavigation>()
    val navigationEvent = _navigationEvent

    init {
        viewModelScope.launch {
            delay(3000) // Show splash for 1.5 seconds
            checkUserStatusAndNavigate()
        }
    }

    private fun checkUserStatusAndNavigate() {
        viewModelScope.launch {
            _navigationEvent.tryEmit(SplashNavigation.Login)
        }
    }
}