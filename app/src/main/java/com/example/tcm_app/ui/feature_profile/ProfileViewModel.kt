package com.example.tcm_app.ui.feature_profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileState(
    val showEditDialog: Boolean = false,
    val selectedSection: String = ""
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    fun onEditClicked() {
        _uiState.update { it.copy(showEditDialog = true) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(showEditDialog = false) }
    }

    fun onSectionSelected(section: String) {
        _uiState.update { it.copy(selectedSection = section, showEditDialog = true) }
    }
}