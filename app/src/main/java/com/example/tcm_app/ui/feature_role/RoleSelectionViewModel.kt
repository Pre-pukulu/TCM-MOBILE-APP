package com.example.tcm_app.ui.feature_role

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RoleSelectionViewModel : ViewModel() {

    private val _selectedRole = mutableStateOf<UserRole?>(null)
    val selectedRole: State<UserRole?> = _selectedRole

    private val _navigationEvent = mutableSingleFireNavigation<RoleSelectionNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onRoleSelected(role: UserRole) {
        _selectedRole.value = role
    }

    fun onContinueClicked() {
        viewModelScope.launch {
            when (_selectedRole.value) {
                UserRole.STUDENT -> _navigationEvent.emit(RoleSelectionNavigation.NavigateToStudentLogin)
                UserRole.TEACHER -> _navigationEvent.emit(RoleSelectionNavigation.NavigateToTeacherLogin)
                null -> { /* Do nothing. Button should be disabled */ }
            }
        }
    }
}

sealed class RoleSelectionNavigation {
    object NavigateToStudentLogin : RoleSelectionNavigation()
    object NavigateToTeacherLogin : RoleSelectionNavigation()
}