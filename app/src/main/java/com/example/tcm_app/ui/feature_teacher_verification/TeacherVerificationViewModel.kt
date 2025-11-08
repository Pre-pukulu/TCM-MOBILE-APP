package com.example.tcm_app.ui.feature_teacher_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TeacherVerificationState(
    val searchQuery: String = "",
    val searchType: String = "Registration Number",
    val searchTypeExpanded: Boolean = false,
    val hasSearched: Boolean = false,
    val isSearching: Boolean = false,
    val searchResult: TeacherSearchResult? = null,
    val searchTypes: List<String> = listOf("Registration Number", "National ID", "Full Name")
)

class TeacherVerificationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherVerificationState())
    val uiState = _uiState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onSearchTypeChanged(type: String) {
        _uiState.update { it.copy(searchType = type, searchQuery = "", searchTypeExpanded = false) }
    }

    fun onSearchTypeExpandedChanged(isExpanded: Boolean) {
        _uiState.update { it.copy(searchTypeExpanded = isExpanded) }
    }

    fun onSearchClicked() {
        if (_uiState.value.searchQuery.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true, hasSearched = true) }
            // Simulate network delay
            delay(1000)
            
            val query = _uiState.value.searchQuery
            val result = if (query.contains("1234") || query.lowercase().contains("banda")) {
                TeacherSearchResult(
                    found = true,
                    name = "John Banda",
                    registrationNumber = "TCM/2024/001234",
                    nationalId = "MWI 1234567890123",
                    status = "Active",
                    licenseExpiry = "14 Jan 2026",
                    school = "Blantyre Primary School",
                    district = "Blantyre",
                    qualifications = "Bachelor of Education",
                    issueDate = "15 Jan 2024"
                )
            } else {
                TeacherSearchResult(found = false)
            }
            _uiState.update { it.copy(isSearching = false, searchResult = result) }
        }
    }
}