package com.example.tcm_app.ui.feature_teacher_registration

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tcm_app.navigation.ObserveSingleFireNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherRegistrationScreen(
    navController: NavController,
    viewModel: TeacherRegistrationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val activity = (LocalContext.current as? Activity)

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            is TeacherRegistrationNavigation.Back -> {
                if (!navController.popBackStack()) {
                    activity?.finish()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teacher Registration") },
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Stepper(currentStep = uiState.currentStep, steps = listOf("Personal", "Address", "Education")) {
                viewModel.onStepClicked(it)
            }
            Spacer(modifier = Modifier.height(24.dp))
            when (uiState.currentStep) {
                0 -> PersonalInfoStep(uiState, viewModel)
                1 -> AddressStep(uiState, viewModel)
                2 -> EducationStep(uiState, viewModel)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (uiState.currentStep > 0) {
                    Button(onClick = viewModel::onPreviousStep) {
                        Text("Previous")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (uiState.currentStep < 2) {
                    Button(onClick = viewModel::onNextStep) {
                        Text("Next")
                    }
                } else {
                    Button(onClick = { /* TODO: Implement registration logic */ }) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

@Composable
fun PersonalInfoStep(uiState: TeacherRegistrationState, viewModel: TeacherRegistrationViewModel) {
    Column {
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = viewModel::onFirstNameChange,
            label = { Text("First Name") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = viewModel::onLastNameChange,
            label = { Text("Last Name") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.middleName,
            onValueChange = viewModel::onMiddleNameChange,
            label = { Text("Middle Name") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.nationalId,
            onValueChange = viewModel::onNationalIdChange,
            label = { Text("National ID") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.dateOfBirth,
            onValueChange = viewModel::onDateOfBirthChange,
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.gender,
            onValueChange = viewModel::onGenderChange,
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.phone,
            onValueChange = viewModel::onPhoneChange,
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun AddressStep(uiState: TeacherRegistrationState, viewModel: TeacherRegistrationViewModel) {
    Column {
        OutlinedTextField(
            value = uiState.district,
            onValueChange = viewModel::onDistrictChange,
            label = { Text("District") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.traditionalAuthority,
            onValueChange = viewModel::onTraditionalAuthorityChange,
            label = { Text("Traditional Authority") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.village,
            onValueChange = viewModel::onVillageChange,
            label = { Text("Village") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun EducationStep(uiState: TeacherRegistrationState, viewModel: TeacherRegistrationViewModel) {
    Column {
        OutlinedTextField(
            value = uiState.msceYear,
            onValueChange = viewModel::onMsceYearChange,
            label = { Text("MSCE Year") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.diploma,
            onValueChange = viewModel::onDiplomaChange,
            label = { Text("Diploma") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.diplomaYear,
            onValueChange = viewModel::onDiplomaYearChange,
            label = { Text("Diploma Year") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.degree,
            onValueChange = viewModel::onDegreeChange,
            label = { Text("Degree") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.degreeYear,
            onValueChange = viewModel::onDegreeYearChange,
            label = { Text("Degree Year") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun Stepper(
    currentStep: Int,
    steps: List<String>,
    onStepClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        steps.forEachIndexed { index, title ->
            Step(
                title = title,
                isCurrent = currentStep == index,
                isCompleted = currentStep > index,
                onClick = { onStepClicked(index) }
            )
            if (index < steps.size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step(
    title: String,
    isCurrent: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    val color = when {
        isCurrent -> MaterialTheme.colorScheme.primary
        isCompleted -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    }
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
