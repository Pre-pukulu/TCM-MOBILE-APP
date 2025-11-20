package mw.gov.tcm.ui.feature_license_application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mw.gov.tcm.data.model.Application
import mw.gov.tcm.data.teacher.TeacherRepository
import javax.inject.Inject

data class LicenseApplicationState(
    val fullName: String = "",
    val tcmNumber: String = "",
    val applicationType: String = "New License",
    val agreeToTerms: Boolean = false,
    val submissionResult: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class LicenseApplicationViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    var uiState by mutableStateOf(LicenseApplicationState())
        private set

    fun onFullNameChange(name: String) {
        uiState = uiState.copy(fullName = name)
    }

    fun onTcmNumberChange(number: String) {
        uiState = uiState.copy(tcmNumber = number)
    }

    fun onAgreeToTermsChange(agreed: Boolean) {
        uiState = uiState.copy(agreeToTerms = agreed)
    }

    fun submitApplication() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            uiState = uiState.copy(submissionResult = "Error: User not logged in.")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val application = Application(
                id = userId,
                fullName = uiState.fullName,
                tcmNumber = uiState.tcmNumber,
                type = uiState.applicationType
            )

            try {
                teacherRepository.submitLicenseApplication(application)
                uiState = uiState.copy(isLoading = false, submissionResult = "Application submitted successfully!")
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    submissionResult = e.message ?: "An unknown error occurred."
                )
            }
        }
    }
}