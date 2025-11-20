package mw.gov.tcm.ui.feature_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import mw.gov.tcm.R
import mw.gov.tcm.navigation.ObserveSingleFireNavigation
import mw.gov.tcm.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherForgotPasswordScreen(
    viewModel: TeacherForgotPasswordViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { 
        when (it) {
            TeacherForgotPasswordNavigation.Back -> onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.forgot_password)) })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (uiState.emailSent) {
                Text(stringResource(id = R.string.password_reset_email_sent))
            } else {
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text(stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.error != null
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                Button(
                    onClick = viewModel::onForgotPasswordClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                ) {
                    Text(stringResource(id = R.string.send_password_reset_email))
                }

                uiState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small)
                    )
                }
            }
        }
    }
}