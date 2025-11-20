package mw.gov.tcm.ui.feature_teacher_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.teacher.TeacherRepository
import javax.inject.Inject

data class TeacherVerificationState(
    val searchQuery: String = "",
    val searchType: String = "Registration Number",
    val searchTypeExpanded: Boolean = false,
    val hasSearched: Boolean = false,
    val isSearching: Boolean = false,
    val searchResult: TeacherSearchResult? = null,
    val searchTypes: List<String> = listOf("Registration Number", "National ID", "Full Name")
)

@HiltViewModel
class TeacherVerificationViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

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
            val result = teacherRepository.verifyTeacher(
                query = _uiState.value.searchQuery,
                searchType = _uiState.value.searchType
            )
            _uiState.update { it.copy(isSearching = false, searchResult = result) }
        }
    }
}