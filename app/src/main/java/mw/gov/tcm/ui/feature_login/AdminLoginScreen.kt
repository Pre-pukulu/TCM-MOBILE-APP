package mw.gov.tcm.ui.feature_login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mw.gov.tcm.navigation.ObserveSingleFireNavigation
import mw.gov.tcm.navigation.Screen

@Composable
fun AdminLoginScreen(
    navController: NavController,
    viewModel: AdminLoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            AdminLoginNavigation.AdminDashboard -> {
                navController.navigate(Screen.AdminDashboard.route) {
                    popUpTo(Screen.AdminLogin.route) { inclusive = true }
                }
            }
            AdminLoginNavigation.ForgotPassword -> {
                navController.navigate(Screen.ForgotPassword.route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Admin Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.error != null
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = viewModel::onPasswordVisibilityToggle) {
                    Icon(
                        if (uiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.error != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = viewModel::onAdminLoginClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Don't have an account? Sign up",
            modifier = Modifier.clickable { navController.navigate(Screen.AdminSignUp.route) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot Password?",
            modifier = Modifier.clickable { viewModel.onForgotPasswordClick() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Not an admin? Login as user",
            modifier = Modifier.clickable { navController.navigate(Screen.RoleSelection.route) }
        )
    }
}