package mw.gov.tcm.ui.feature_teacher_verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherVerificationScreen(
    navController: NavController,
    viewModel: TeacherVerificationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Teacher") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.VerifiedUser,
                        null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            "Public Verification",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Verify teacher registration status",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Search Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Search Teacher",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Search Type Selector
                    ExposedDropdownMenuBox(
                        expanded = uiState.searchTypeExpanded,
                        onExpandedChange = viewModel::onSearchTypeExpandedChanged
                    ) {
                        OutlinedTextField(
                            value = uiState.searchType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Search By") },
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, null)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = uiState.searchTypeExpanded,
                            onDismissRequest = { viewModel.onSearchTypeExpandedChanged(false) }
                        ) {
                            uiState.searchTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = { viewModel.onSearchTypeChanged(type) }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Search Input
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = viewModel::onSearchQueryChanged,
                        label = { Text("Enter ${uiState.searchType}") },
                        placeholder = {
                            Text(
                                when (uiState.searchType) {
                                    "Registration Number" -> "e.g., TCM/2024/001234"
                                    "National ID" -> "e.g., MWI 1234567890123"
                                    else -> "e.g., John Banda"
                                }
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = if (uiState.searchType == "Full Name")
                                KeyboardType.Text else KeyboardType.Number,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = { viewModel.onSearchClicked() }
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    // Search Button
                    Button(
                        onClick = viewModel::onSearchClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = uiState.searchQuery.isNotBlank() && !uiState.isSearching
                    ) {
                        if (uiState.isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Icon(Icons.Default.Search, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Search Teacher", fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Search Results
            if (uiState.hasSearched && uiState.searchResult != null) {
                val searchResult = uiState.searchResult!!
                if (searchResult.found) {
                    // Teacher Found - Success Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            Color(0xFF43A047),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        null,
                                        tint = Color.White,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(
                                        "✓ Verified Teacher",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32)
                                    )
                                    Text(
                                        "This teacher is registered with TCM",
                                        fontSize = 13.sp,
                                        color = Color(0xFF558B2F)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Teacher Details Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            // Profile Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        searchResult.name.split(" ")
                                            .mapNotNull { it.firstOrNull() }
                                            .take(2)
                                            .joinToString(""),
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(
                                        searchResult.name,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFF43A047).copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            searchResult.status,
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 4.dp
                                            ),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E7D32)
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(24.dp))
                            Divider()
                            Spacer(Modifier.height(16.dp))

                            // Details
                            Text(
                                "Registration Details",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            VerificationDetailRow(
                                "Registration Number",
                                searchResult.registrationNumber
                            )
                            VerificationDetailRow(
                                "National ID",
                                searchResult.nationalId
                            )
                            VerificationDetailRow(
                                "Issue Date",
                                searchResult.issueDate
                            )
                            VerificationDetailRow(
                                "License Expiry",
                                searchResult.licenseExpiry
                            )

                            Spacer(Modifier.height(16.dp))
                            Divider()
                            Spacer(Modifier.height(16.dp))

                            Text(
                                "Employment Information",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            VerificationDetailRow(
                                "Current School",
                                searchResult.school
                            )
                            VerificationDetailRow(
                                "District",
                                searchResult.district
                            )
                            VerificationDetailRow(
                                "Qualifications",
                                searchResult.qualifications
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Warning Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF3E0)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Always verify the teacher's physical ID card matches this information",
                                fontSize = 13.sp,
                                color = Color(0xFFE65100)
                            )
                        }
                    }

                } else {
                    // Teacher Not Found
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Cancel,
                                null,
                                tint = Color(0xFFE53935),
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Teacher Not Found",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC62828)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "No teacher found with this ${uiState.searchType}",
                                fontSize = 14.sp,
                                color = Color(0xFFB71C1C)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Please verify you entered the correct information",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Help Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "How to Verify",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    HelpStep(
                        number = "1",
                        text = "Select search type (Registration Number, National ID, or Name)"
                    )
                    HelpStep(
                        number = "2",
                        text = "Enter the teacher's information"
                    )
                    HelpStep(
                        number = "3",
                        text = "Click Search to verify registration status"
                    )
                    HelpStep(
                        number = "4",
                        text = "Confirm the details match the physical ID card"
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

data class TeacherSearchResult(
    val found: Boolean,
    val name: String = "",
    val registrationNumber: String = "",
    val nationalId: String = "",
    val status: String = "",
    val licenseExpiry: String = "",
    val school: String = "",
    val district: String = "",
    val qualifications: String = "",
    val issueDate: String = ""
)

@Composable
fun VerificationDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun HelpStep(number: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                number,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
    }
}