package com.example.tcm_app.ui.feature_license_management

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tcm_app.navigation.ObserveSingleFireNavigation
import com.example.tcm_app.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseManagementScreen(
    navController: NavController,
    viewModel: LicenseManagementViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            LicenseManagementNavigation.Profile -> navController.navigate(Screen.Profile.route)
            LicenseManagementNavigation.Settings -> navController.navigate(Screen.Settings.route)
            LicenseManagementNavigation.Login -> navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // License Card (Digital ID)
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
                            uiState.licenseNumber,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(16.dp))

                        // Teacher Name
                        Text(
                            uiState.teacherName.uppercase(),
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
                                        uiState.issueDate,
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
                                        uiState.expiryDate,
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
                                            uiState.status,
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

            // Status Alert
            if (uiState.daysToExpiry < 90) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Renewal Reminder",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Your license expires in ${uiState.daysToExpiry} days",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

            // Quick Actions
            Text(
                "Quick Actions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionCard(
                    icon = Icons.Default.Refresh,
                    title = "Renew",
                    subtitle = "License",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )

                ActionCard(
                    icon = Icons.Default.Download,
                    title = "Download",
                    subtitle = "License",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                ActionCard(
                    icon = Icons.Default.Share,
                    title = "Share",
                    subtitle = "License",
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            // License Details
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
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    DetailRow("Registration Number", "TCM/REG/2023/5678")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    DetailRow("Category", "Qualified Teacher")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    DetailRow("Subject Area", "Primary Education")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    DetailRow("Valid for", "All Malawi Schools")
                }
            }

            Spacer(Modifier.height(24.dp))

            // Renewal History
            Text(
                "Renewal History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            val renewalHistory = listOf(
                RenewalRecord("15 Jan 2024", "2 years", "Renewed"),
                RenewalRecord("15 Jan 2022", "2 years", "Renewed"),
                RenewalRecord("15 Jan 2020", "2 years", "Initial Issue")
            )

            renewalHistory.forEach { record ->
                RenewalHistoryCard(record)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun ActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
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

data class RenewalRecord(
    val date: String,
    val period: String,
    val status: String
)

@Composable
fun RenewalHistoryCard(record: RenewalRecord) {
    val color = if (record.status == "Initial Issue") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.1f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    record.status,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    record.date,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Text(
                record.period,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}