package com.example.tcm_app.ui.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tcm_app.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class SplashNavigation {
    object Login : SplashNavigation()
    object Dashboard : SplashNavigation()
}

class SplashViewModel : ViewModel() {

    private val _navigationEvent = mutableSingleFireNavigation<SplashNavigation>()
    val navigationEvent = _navigationEvent

    init {
        viewModelScope.launch {
            delay(3000) // Show splash for 3 seconds
            checkUserStatusAndNavigate()
        }
    }

    private fun checkUserStatusAndNavigate() {
        viewModelScope.launch {
            // TODO: Implement actual user status check from a data source
            val isUserLoggedIn = false // Placeholder
            if (isUserLoggedIn) {
                _navigationEvent.tryEmit(SplashNavigation.Dashboard)
            } else {
                _navigationEvent.tryEmit(SplashNavigation.Login)
            }
        }
    }
}