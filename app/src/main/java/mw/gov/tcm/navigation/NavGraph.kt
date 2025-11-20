package mw.gov.tcm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mw.gov.tcm.ui.feature_admin.AdminDashboardScreen
import mw.gov.tcm.ui.feature_application_status.ApplicationStatusScreen
import mw.gov.tcm.ui.feature_cpd_tracking.CPDTrackingScreen
import mw.gov.tcm.ui.feature_dashboard.DashboardScreen
import mw.gov.tcm.ui.feature_license_application.LicenseApplicationScreen
import mw.gov.tcm.ui.feature_license_management.LicenseManagementScreen
import mw.gov.tcm.ui.feature_login.AdminLoginScreen
import mw.gov.tcm.ui.feature_login.AdminSignUpScreen
import mw.gov.tcm.ui.feature_login.ForgotPasswordScreen
import mw.gov.tcm.ui.feature_login.LoginScreen
import mw.gov.tcm.ui.feature_login.StudentLoginScreen
import mw.gov.tcm.ui.feature_login.TeacherForgotPasswordScreen
import mw.gov.tcm.ui.feature_notifications.NotificationsScreen
import mw.gov.tcm.ui.feature_profile.ProfileScreen
import mw.gov.tcm.ui.feature_register.RegisterScreen
import mw.gov.tcm.ui.feature_role.RoleSelectionScreen
import mw.gov.tcm.ui.feature_settings.SettingsScreen
import mw.gov.tcm.ui.feature_signup.SignupScreen
import mw.gov.tcm.ui.feature_splash.SplashScreen
import mw.gov.tcm.ui.feature_student.StudentDashboardScreen
import mw.gov.tcm.ui.feature_student.StudentIndexingScreen
import mw.gov.tcm.ui.feature_teacher_registration.TeacherRegistrationScreen
import mw.gov.tcm.ui.feature_teacher_verification.TeacherVerificationScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        authGraph(navController)
        mainGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    composable(Screen.RoleSelection.route) {
        RoleSelectionScreen(navController = navController)
    }
    composable(Screen.Login.route) {
        LoginScreen(navController = navController)
    }
    composable(Screen.Signup.route) {
        SignupScreen(navController = navController)
    }
    composable(Screen.StudentLogin.route) {
        StudentLoginScreen(navController = navController)
    }
    composable(Screen.AdminLogin.route) {
        AdminLoginScreen(navController = navController)
    }
    composable(Screen.AdminSignUp.route) {
        AdminSignUpScreen(navController = navController)
    }
    composable(Screen.ForgotPassword.route) {
        ForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
    }
    composable(Screen.TeacherForgotPassword.route) {
        TeacherForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
    }
    composable(Screen.Register.route) {
        RegisterScreen(navController = navController)
    }
    composable(Screen.TeacherVerification.route) {
        TeacherVerificationScreen(navController = navController)
    }
    composable(Screen.StudentIndexing.route) {
        StudentIndexingScreen(navController = navController)
    }
}

private fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable(Screen.Splash.route) {
        SplashScreen(navController = navController)
    }
    composable(Screen.Dashboard.route) {
        DashboardScreen(navController = navController)
    }
    composable(Screen.Profile.route) {
        ProfileScreen(navController = navController)
    }
    composable(
        route = Screen.TeacherRegistration.routeWithArgs,
        arguments = Screen.TeacherRegistration.arguments
    ) { backStackEntry ->
        val step = backStackEntry.arguments?.getInt("step") ?: 0
        TeacherRegistrationScreen(navController = navController, initialStep = step)
    }
    composable(Screen.LicenseManagement.route) {
        LicenseManagementScreen(navController = navController)
    }
    composable(Screen.LicenseApplication.route) {
        LicenseApplicationScreen(navController = navController)
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
    composable(Screen.Settings.route) {
        SettingsScreen(navController = navController)
    }
    composable(Screen.Notifications.route) {
        NotificationsScreen(navController = navController)
    }
    composable(Screen.StudentDashboard.route) {
        StudentDashboardScreen(navController = navController)
    }
}
