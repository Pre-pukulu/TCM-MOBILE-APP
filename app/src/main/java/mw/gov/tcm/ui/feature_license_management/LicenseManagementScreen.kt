package mw.gov.tcm.ui.feature_license_management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mw.gov.tcm.data.model.License
import mw.gov.tcm.data.model.Teacher
import mw.gov.tcm.navigation.ObserveSingleFireNavigation
import mw.gov.tcm.navigation.Screen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseManagementScreen(
    navController: NavController,
    viewModel: LicenseManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            is LicenseManagementNavigation.Profile -> navController.navigate(Screen.Profile.route)
            is LicenseManagementNavigation.Settings -> navController.navigate(Screen.Settings.route)
            is LicenseManagementNavigation.Login -> navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
            is LicenseManagementNavigation.ApplyForLicense -> navController.navigate(Screen.LicenseApplication.route)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My License") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
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
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (uiState.teacher != null && uiState.license != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .verticalScroll(rememberScrollState())
                ) {
                    LicenseCard(uiState.teacher!!, uiState.license!!)

                    Spacer(Modifier.height(24.dp))

                    QuickActions(uiState.license!!)

                    Spacer(Modifier.height(24.dp))

                    LicenseDetails()

                    Spacer(Modifier.height(24.dp))

                    RenewalHistory()

                    Spacer(Modifier.height(24.dp))
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.teacher == null) {
                        Text(
                            "Could not load your profile. Please try again later.",
                            textAlign = TextAlign.Center
                        )
                    } else { // Implies license is null
                        Text("No License Found", style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "It looks like you don't have a teaching license yet. You can apply for one through the app.",
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = viewModel::onApplyForLicenseClick) {
                            Text("Apply for License")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LicenseCard(teacher: Teacher, license: License) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            // Background gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            "TEACHERS COUNCIL",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "OF MALAWI",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // TCM Logo
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "TCM",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // License Type
                Text(
                    "TEACHING LICENSE",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                // License Number
                Text(
                    license.licenseNumber,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                // Teacher Name
                Text(
                    "${teacher.firstName} ${teacher.lastName}".uppercase(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(24.dp))

                // Dates
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Blue.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "ISSUED",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = license.dateOfIssue,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Column {
                            Text(
                                "EXPIRES",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = license.expiryDate,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Column {
                            Text(
                                "STATUS",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    license.status,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LicenseDetails() {
    Text(
        "License Details",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailRow("License Type", "Primary Teaching License")
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            DetailRow("Registration Number", "TCM/REG/2023/5678")
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            DetailRow("Category", "Qualified Teacher")
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            DetailRow("Subject Area", "Primary Education")
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            DetailRow("Valid for", "All Malawi Schools")
        }
    }
}

@Composable
fun QuickActions(license: License) {
    val isRenewalAvailable = remember(license.expiryDate) {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        try {
            val expiry = sdf.parse(license.expiryDate)
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 90)
            expiry?.before(calendar.time) ?: false
        } catch (e: Exception) {
            false
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)){
        Text(
            "Quick Actions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isRenewalAvailable) {
                QuickActionCard(
                    modifier = Modifier.weight(1f),
                    title = "Renew",
                    subtitle = "License",
                    icon = Icons.Default.Refresh,
                    color = Color(0xFFE8F5E9),
                    onClick = { /* TODO */ }
                )
            }
            QuickActionCard(
                modifier = Modifier.weight(1f),
                title = "Download",
                subtitle = "License",
                icon = Icons.Default.Download,
                color = Color(0xFFE3F2FD),
                onClick = { /* TODO */ }
            )
            QuickActionCard(
                modifier = Modifier.weight(1f),
                title = "Share",
                subtitle = "License",
                icon = Icons.Default.Share,
                color = Color(0xFFF3E5F5),
                onClick = { /* TODO */ }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun RenewalHistory() {
    Text(
        "Renewal History",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        // Placeholder for now
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            Text("No renewal history found.")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun Date.format(pattern: String = "dd MMM yyyy"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}
