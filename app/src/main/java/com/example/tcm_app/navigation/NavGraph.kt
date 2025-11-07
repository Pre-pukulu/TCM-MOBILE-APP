package com.example.tcm_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tcm_app.ui.feature_admin.AdminDashboardScreen
import com.example.tcm_app.ui.feature_application_status.ApplicationStatusScreen
import com.example.tcm_app.ui.feature_cpd_tracking.CPDTrackingScreen
import com.example.tcm_app.ui.feature_dashboard.DashboardScreen
import com.example.tcm_app.ui.feature_license_management.LicenseManagementScreen
import com.example.tcm_app.ui.feature_login.LoginScreen
import com.example.tcm_app.ui.feature_profile.ProfileScreen
import com.example.tcm_app.ui.feature_register.RegisterScreen
import com.example.tcm_app.ui.feature_splash.SplashScreen
import com.example.tcm_app.ui.feature_teacher_registration.TeacherRegistrationScreen
import com.example.tcm_app.ui.feature_teacher_verification.TeacherVerificationScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.TeacherVerification.route) {
            TeacherVerificationScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.TeacherRegistration.route) {
            TeacherRegistrationScreen(navController = navController)
        }
        composable(Screen.LicenseManagement.route) {
            LicenseManagementScreen(navController = navController)
        }
        composable(Screen.CPDTracking.route) {
            CPDTrackingScreen(navController = navController)
        }
        composable(Screen.ApplicationStatus.route) {
            ApplicationStatusScreen(navController = navController)
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(navController = navController)
        }
    }
}