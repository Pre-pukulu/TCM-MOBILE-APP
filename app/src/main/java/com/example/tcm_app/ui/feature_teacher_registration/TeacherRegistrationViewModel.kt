package com.example.tcm_app.ui.feature_teacher_registration

import androidx.lifecycle.ViewModel
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class TeacherRegistrationNavigation {
    object Back : TeacherRegistrationNavigation()
}

data class TeacherRegistrationState(
    val currentStep: Int = 0,
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
    val degreeYear: String = ""
)

class TeacherRegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherRegistrationState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<TeacherRegistrationNavigation>()
    val navigationEvent = _navigationEvent

    fun onStepClicked(stepIndex: Int) {
        // Allow navigation to any step
        _uiState.update { it.copy(currentStep = stepIndex) }
    }

    fun onNextStep() {
        if (_uiState.value.currentStep < 3) { // 3 is the last step index
            _uiState.update { it.copy(currentStep = it.currentStep + 1) }
        }
    }

    fun onPreviousStep() {
        if (_uiState.value.currentStep > 0) {
            _uiState.update { it.copy(currentStep = it.currentStep - 1) }
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
}