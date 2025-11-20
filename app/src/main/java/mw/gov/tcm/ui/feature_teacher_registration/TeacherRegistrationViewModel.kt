package mw.gov.tcm.ui.feature_teacher_registration

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.model.Teacher
import mw.gov.tcm.data.settings.RegistrationProgressRepository
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import javax.inject.Inject

sealed class TeacherRegistrationNavigation {
    object Back : TeacherRegistrationNavigation()
    object Profile : TeacherRegistrationNavigation()
    object Settings : TeacherRegistrationNavigation()
    object Login : TeacherRegistrationNavigation()
    object Dashboard : TeacherRegistrationNavigation()
}

data class TeacherRegistrationState(
    val currentStep: Int = 0,
    val showMenu: Boolean = false,
    // Personal Info
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val nationalId: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val phone: String = "",
    val email: String = "",
    // Address
    val district: String = "",
    val traditionalAuthority: String = "",
    val village: String = "",
    // Education
    val msceYear: String = "",
    val diploma: String = "",
    val diplomaYear: String = "",
    val degree: String = "",
    val degreeYear: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class TeacherRegistrationViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val registrationProgressRepository: RegistrationProgressRepository,
    private val savedStateHandle: SavedStateHandle,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherRegistrationState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<TeacherRegistrationNavigation>()
    val navigationEvent = _navigationEvent

    fun setInitialStep(step: Int) {
        _uiState.update { it.copy(currentStep = step) }
    }

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
    }

    fun onProfileClick() {
        _navigationEvent.tryEmit(TeacherRegistrationNavigation.Profile)
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(TeacherRegistrationNavigation.Settings)
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            registrationProgressRepository.setRegistrationStep(0)
            _navigationEvent.tryEmit(TeacherRegistrationNavigation.Login)
        }
    }

    fun onStepClicked(stepIndex: Int) {
        _uiState.update { it.copy(currentStep = stepIndex) }
        viewModelScope.launch {
            registrationProgressRepository.setRegistrationStep(stepIndex)
        }
    }

    private fun isCurrentStepValid(): Boolean {
        val state = _uiState.value
        return when (state.currentStep) {
            0 -> state.firstName.isNotBlank() &&
                    state.lastName.isNotBlank() &&
                    state.nationalId.isNotBlank() &&
                    state.dateOfBirth.isNotBlank() &&
                    state.gender.isNotBlank() &&
                    state.phone.isNotBlank() &&
                    state.email.isNotBlank()
            1 -> state.district.isNotBlank() &&
                    state.traditionalAuthority.isNotBlank() &&
                    state.village.isNotBlank()
            2 -> state.msceYear.isNotBlank() &&
                    state.diploma.isNotBlank() &&
                    state.diplomaYear.isNotBlank() &&
                    state.degree.isNotBlank() &&
                    state.degreeYear.isNotBlank()
            else -> true // No validation for other steps
        }
    }

    fun onNextStep() {
        if (isCurrentStepValid()) {
            if (_uiState.value.currentStep < 4) { // 4 is the last step index
                val nextStep = _uiState.value.currentStep + 1
                _uiState.update { it.copy(currentStep = nextStep, error = null) }
                viewModelScope.launch {
                    registrationProgressRepository.setRegistrationStep(nextStep)
                }
            }
        } else {
            _uiState.update { it.copy(error = "Please fill in all fields to continue.") }
        }
    }

    fun onPreviousStep() {
        if (_uiState.value.currentStep > 0) {
            val prevStep = _uiState.value.currentStep - 1
            _uiState.update { it.copy(currentStep = prevStep) }
            viewModelScope.launch {
                registrationProgressRepository.setRegistrationStep(prevStep)
            }
        }
    }

    fun onFirstNameChange(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun onLastNameChange(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun onMiddleNameChange(middleName: String) {
        _uiState.update { it.copy(middleName = middleName) }
    }

    fun onNationalIdChange(nationalId: String) {
        _uiState.update { it.copy(nationalId = nationalId) }
    }

    fun onDateOfBirthChange(dateOfBirth: String) {
        _uiState.update { it.copy(dateOfBirth = dateOfBirth) }
    }

    fun onGenderChange(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun onPhoneChange(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onDistrictChange(district: String) {
        _uiState.update { it.copy(district = district) }
    }

    fun onTraditionalAuthorityChange(traditionalAuthority: String) {
        _uiState.update { it.copy(traditionalAuthority = traditionalAuthority) }
    }

    fun onVillageChange(village: String) {
        _uiState.update { it.copy(village = village) }
    }

    fun onMsceYearChange(msceYear: String) {
        _uiState.update { it.copy(msceYear = msceYear) }
    }

    fun onDiplomaChange(diploma: String) {
        _uiState.update { it.copy(diploma = diploma) }
    }

    fun onDiplomaYearChange(diplomaYear: String) {
        _uiState.update { it.copy(diplomaYear = diplomaYear) }
    }

    fun onDegreeChange(degree: String) {
        _uiState.update { it.copy(degree = degree) }
    }

    fun onDegreeYearChange(degreeYear: String) {
        _uiState.update { it.copy(degreeYear = degreeYear) }
    }

    fun onBackClick() {
        _navigationEvent.tryEmit(TeacherRegistrationNavigation.Back)
    }

    fun build(userId: String): Teacher {
        val currentState = _uiState.value
        return Teacher(
            id = userId,
            registrationStep = currentState.currentStep,
            firstName = currentState.firstName,
            lastName = currentState.lastName,
            middleName = currentState.middleName,
            nationalId = currentState.nationalId,
            dateOfBirth = currentState.dateOfBirth,
            gender = currentState.gender,
            phone = currentState.phone,
            email = currentState.email,
            district = currentState.district,
            traditionalAuthority = currentState.traditionalAuthority,
            village = currentState.village,
            msceYear = currentState.msceYear,
            diploma = currentState.diploma,
            diplomaYear = currentState.diplomaYear,
            degree = currentState.degree,
            degreeYear = currentState.degreeYear
        )
    }

    fun submitRegistration() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(error = "User not logged in") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val teacher = build(userId)
                teacherRepository.saveTeacher(teacher)
                _uiState.update { it.copy(isLoading = false) }
                registrationProgressRepository.setRegistrationStep(0)
                _navigationEvent.tryEmit(TeacherRegistrationNavigation.Dashboard)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}