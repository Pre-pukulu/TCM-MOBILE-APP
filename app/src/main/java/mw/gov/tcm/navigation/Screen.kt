package mw.gov.tcm.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    open val routeWithArgs: String = route

    object Splash : Screen("splash")
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object StudentLogin : Screen("student_login")
    object AdminLogin : Screen("admin_login")
    object AdminSignUp : Screen("admin_signup")
    object ForgotPassword : Screen("forgot_password")
    object TeacherForgotPassword : Screen("teacher_forgot_password")
    object Register : Screen("register")
    object TeacherVerification : Screen("teacher_verification")
    object StudentIndexing : Screen("student_indexing")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object TeacherRegistration : Screen(
        route = "teacher_registration",
        arguments = listOf(navArgument("step") { type = NavType.IntType; defaultValue = 0 })
    ) {
        override val routeWithArgs: String = "$route?step={step}"
    }

    object LicenseManagement : Screen("license_management")
    object LicenseApplication : Screen("license_application")
    object CPDTracking : Screen("cpd_tracking")
    object ApplicationStatus : Screen("application_status")
    object AdminDashboard : Screen("admin_dashboard")
    object Settings : Screen("settings")
    object Notifications : Screen("notifications")
    object StudentDashboard : Screen("student_dashboard")
}