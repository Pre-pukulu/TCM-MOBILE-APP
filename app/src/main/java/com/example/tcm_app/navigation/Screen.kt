package com.example.tcm_app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Register : Screen("register")
    object TeacherVerification : Screen("teacher_verification")
    object Profile : Screen("profile")
    object TeacherRegistration : Screen("teacher_registration")
    object LicenseManagement : Screen("license_management")
    object CPDTracking : Screen("cpd_tracking")
    object ApplicationStatus : Screen("application_status")
    object AdminDashboard : Screen("admin_dashboard")
    object AdminLogin : Screen("admin_login")
    object Notifications : Screen("notifications")
    object Signup : Screen("signup")
    object Settings : Screen("settings")

    object RoleSelection : Screen("role_selection")
    object StudentLogin : Screen("student_login")
    object StudentIndexing : Screen("student_indexing")
    object StudentDashboard : Screen("student_dashboard")

}