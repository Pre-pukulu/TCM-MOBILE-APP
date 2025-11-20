package mw.gov.tcm.ui.feature_application_status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mw.gov.tcm.data.repository.ApplicationStatusRepository
import javax.inject.Inject

@HiltViewModel
class ApplicationStatusViewModel @Inject constructor(
    private val repository: ApplicationStatusRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApplicationStatusUiState())
    val uiState: StateFlow<ApplicationStatusUiState> = _uiState.asStateFlow()

    init {
        fetchApplicationStatus()
    }

    private fun fetchApplicationStatus() {
        viewModelScope.launch {
            try {
                val (stages, documents) = repository.getApplicationStatus()
                _uiState.value = ApplicationStatusUiState(
                    currentStage = 2, // This is still hardcoded, will be dynamic later
                    stages = stages,
                    documents = documents,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = ApplicationStatusUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}

data class ApplicationStatusUiState(
    val currentStage: Int = 0,
    val stages: List<TimelineStage> = emptyList(),
    val documents: List<Document> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)