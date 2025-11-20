package mw.gov.tcm.ui.feature_teacher_registration

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mw.gov.tcm.navigation.ObserveSingleFireNavigation
import mw.gov.tcm.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherRegistrationScreen(
    navController: NavController,
    viewModel: TeacherRegistrationViewModel = hiltViewModel(),
    initialStep: Int = 0
) {
    val uiState by viewModel.uiState.collectAsState()
    val activity = (LocalContext.current as? Activity)
    val totalSteps = 5 // Increased total steps to include Payment

    LaunchedEffect(initialStep) {
        viewModel.setInitialStep(initialStep)
    }

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation: TeacherRegistrationNavigation ->
        when (navigation) {
            is TeacherRegistrationNavigation.Back -> {
                if (!navController.popBackStack()) {
                    activity?.finish()
                }
            }
            is TeacherRegistrationNavigation.Profile -> navController.navigate(Screen.Profile.route)
            is TeacherRegistrationNavigation.Settings -> navController.navigate(Screen.Settings.route)
            is TeacherRegistrationNavigation.Login -> navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
            is TeacherRegistrationNavigation.Dashboard -> navController.navigate(Screen.Dashboard.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teacher Registration") },
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::onMenuToggled) {
                        Icon(Icons.Default.MoreVert, "Menu")
                    }
                    DropdownMenu(
                        expanded = uiState.showMenu,
                        onDismissRequest = viewModel::onMenuToggled
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = {
                                viewModel.onMenuToggled()
                                viewModel.onProfileClick()
                            },
                            leadingIcon = { Icon(Icons.Default.Person, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                viewModel.onMenuToggled()
                                viewModel.onSettingsClick()
                            },
                            leadingIcon = { Icon(Icons.Default.Settings, null) }
                        )
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                viewModel.onMenuToggled()
                                viewModel.onLogoutClick()
                            },
                            leadingIcon = { Icon(Icons.Default.Logout, null) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                modifier = Modifier.height(80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.currentStep > 0) {
                        OutlinedButton(
                            onClick = viewModel::onPreviousStep,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Previous")
                        }
                        Spacer(Modifier.width(12.dp))
                    } else {
                        Spacer(Modifier.weight(1f))
                        Spacer(Modifier.width(12.dp))
                    }

                    Button(
                        onClick = {
                            if (uiState.currentStep < totalSteps - 1) {
                                viewModel.onNextStep()
                            } else {
                                viewModel.submitRegistration()
                            }
                        },
                        enabled = !uiState.isLoading,
                        modifier = Modifier.weight(1f)
                    ) {
                        if(uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text(if (uiState.currentStep == totalSteps - 1) "Submit & Pay" else "Next")
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                if (uiState.currentStep == totalSteps - 1) Icons.Default.Check
                                else Icons.AutoMirrored.Filled.ArrowForward,
                                null,
                                Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                )
            }
            StepProgressIndicator(
                currentStep = uiState.currentStep,
                totalSteps = totalSteps,
                onStepClicked = { viewModel.onStepClicked(it) }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                when (uiState.currentStep) {
                    0 -> PersonalInfoStep(uiState, viewModel)
                    1 -> AddressStep(uiState, viewModel)
                    2 -> EducationStep(uiState, viewModel)
                    3 -> DocumentsStep()
                    4 -> PaymentStep()
                }
            }
        }
    }
}

@Composable
fun StepProgressIndicator(currentStep: Int, totalSteps: Int, onStepClicked: (Int) -> Unit) {
    val steps = listOf("Personal", "Address", "Education", "Documents", "Payment") // Added Payment

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            steps.forEachIndexed { index, step ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onStepClicked(index) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (index <= currentStep)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index < currentStep) {
                            Icon(
                                Icons.Default.Check,
                                null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                "${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        step,
                        fontSize = 12.sp,
                        color = if (index <= currentStep)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray,
                        fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal
                    )
                }

                if (index < steps.size - 1) {
                    Box(
                        modifier = Modifier
                            .weight(0.5f)
                            .height(2.dp)
                            .align(Alignment.CenterVertically)
                            .background(
                                if (index < currentStep)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color.LightGray
                            )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoStep(uiState: TeacherRegistrationState, viewModel: TeacherRegistrationViewModel) {
    var genderExpanded by remember { mutableStateOf(false) }

    Column {
        SectionHeader("Personal Information")

        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = viewModel::onFirstNameChange,
            label = { Text("First Name *") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = viewModel::onLastNameChange,
            label = { Text("Last Name *") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.middleName,
            onValueChange = viewModel::onMiddleNameChange,
            label = { Text("Middle Name (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.nationalId,
            onValueChange = viewModel::onNationalIdChange,
            label = { Text("National ID *") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.dateOfBirth,
            onValueChange = viewModel::onDateOfBirthChange,
            label = { Text("Date of Birth *") },
            placeholder = { Text("DD/MM/YYYY") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                Icon(Icons.Default.CalendarToday, null)
            }
        )
        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = genderExpanded,
            onExpandedChange = { genderExpanded = it }
        ) {
            OutlinedTextField(
                value = uiState.gender,
                onValueChange = {},
                readOnly = true,
                label = { Text("Gender *") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp)
            )

            ExposedDropdownMenu(
                expanded = genderExpanded,
                onDismissRequest = { genderExpanded = false }
            ) {
                listOf("Male", "Female").forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            viewModel.onGenderChange(option)
                            genderExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.phone,
            onValueChange = viewModel::onPhoneChange,
            label = { Text("Phone Number *") },
            placeholder = { Text("+265 888 123 456") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = { Icon(Icons.Default.Phone, null) }
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email Address *") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Default.Email, null) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressStep(uiState: TeacherRegistrationState, viewModel: TeacherRegistrationViewModel) {
    var districtExpanded by remember { mutableStateOf(false) }
    val districts = listOf(
        "Blantyre", "Lilongwe", "Mzuzu", "Zomba", "Kasungu",
        "Mangochi", "Salima", "Karonga", "Dedza", "Balaka"
    )

    Column {
        SectionHeader("Address Information")

        ExposedDropdownMenuBox(
            expanded = districtExpanded,
            onExpandedChange = { districtExpanded = it }
        ) {
            OutlinedTextField(
                value = uiState.district,
                onValueChange = {},
                readOnly = true,
                label = { Text("District *") },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp)
            )

            ExposedDropdownMenu(
                expanded = districtExpanded,
                onDismissRequest = { districtExpanded = false }
            ) {
                districts.forEach { d ->
                    DropdownMenuItem(
                        text = { Text(d) },
                        onClick = {
                            viewModel.onDistrictChange(d)
                            districtExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.traditionalAuthority,
            onValueChange = viewModel::onTraditionalAuthorityChange,
            label = { Text("Traditional Authority *") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.village,
            onValueChange = viewModel::onVillageChange,
            label = { Text("Village/Area *") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun EducationStep(uiState: TeacherRegistrationState, viewModel: TeacherRegistrationViewModel) {
    Column {
        SectionHeader("Education Qualifications")

        Text(
            "MSCE Certificate",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.msceYear,
            onValueChange = viewModel::onMsceYearChange,
            label = { Text("Year Obtained *") },
            placeholder = { Text("e.g., 2015") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Teaching Diploma",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.diploma,
            onValueChange = viewModel::onDiplomaChange,
            label = { Text("Diploma Name") },
            placeholder = { Text("e.g., Diploma in Primary Education") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.diplomaYear,
            onValueChange = viewModel::onDiplomaYearChange,
            label = { Text("Year Obtained") },
            placeholder = { Text("e.g., 2018") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Bachelor's Degree (Optional)",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.degree,
            onValueChange = viewModel::onDegreeChange,
            label = { Text("Degree Name") },
            placeholder = { Text("e.g., Bachelor of Education") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.degreeYear,
            onValueChange = viewModel::onDegreeYearChange,
            label = { Text("Year Obtained") },
            placeholder = { Text("e.g., 2022") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun DocumentsStep() {
    val documents = listOf(
        "Passport Photo" to "Required",
        "National ID" to "Required",
        "MSCE Certificate" to "Required",
        "Teaching Diploma" to "Required",
        "Degree Certificate" to "Optional",
        "Police Report" to "Required"
    )

    Column {
        SectionHeader("Upload Documents")

        Text(
            "Please upload clear copies of the following documents:",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        documents.forEach { (doc, status) ->
            DocumentUploadCard(
                documentName = doc,
                status = status
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun DocumentUploadCard(documentName: String, status: String) {
    var uploaded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (uploaded) Color(0xFFE8F5E9) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    documentName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    status,
                    fontSize = 12.sp,
                    color = if (status == "Required") Color(0xFFE53935) else Color.Gray
                )
                if (uploaded) {
                    Text(
                        "✓ Uploaded",
                        fontSize = 12.sp,
                        color = Color(0xFF43A047),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Button(
                onClick = { uploaded = !uploaded },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uploaded)
                        Color(0xFF43A047)
                    else
                        MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    if (uploaded) Icons.Default.Check else Icons.Default.Upload,
                    null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(if (uploaded) "Done" else "Upload")
            }
        }
    }
}

@Composable
fun PaymentStep() {
    Column {
        SectionHeader("Payment Details")

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Please complete the payment to finalize your registration.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Registration Fee:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Text("MWK 20,000", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                Text(
                    "Select Payment Method:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // TODO: Replace with actual payment method selection
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = {}, modifier = Modifier.weight(1f)) { Text("Airtel Money") }
                    Button(onClick = {}, modifier = Modifier.weight(1f)) { Text("TNM Mpamba") }
                }
            }
        }
    }
}


@Composable
fun SectionHeader(text: String) {
    Text(
        text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}