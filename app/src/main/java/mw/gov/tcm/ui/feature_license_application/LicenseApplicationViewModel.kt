package mw.gov.tcm.ui.feature_license_application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mw.gov.tcm.data.Result
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
    private val teacherRepository: TeacherRepository
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
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val application = Application(
                fullName = uiState.fullName,
                tcmNumber = uiState.tcmNumber,
                type = uiState.applicationType
            )

            teacherRepository.submitLicenseApplication(application)
                .onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            uiState = uiState.copy(isLoading = false, submissionResult = "Application submitted successfully!")
                        }
                        is Result.Error -> {
                            uiState = uiState.copy(
                                isLoading = false,
                                submissionResult = result.exception.message ?: "An unknown error occurred."
                            )
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}