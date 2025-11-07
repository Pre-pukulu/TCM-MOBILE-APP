package com.example.tcm_app.ui.feature_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tcm_app.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::onEditClicked) {
                        Icon(Icons.Default.Edit, "Edit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
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
            // Profile Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Photo
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "JB",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "John Banda",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Primary School Teacher",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF43A047).copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                null,
                                tint = Color(0xFF43A047),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Verified Teacher",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF43A047)
                            )
                        }
                    }
                }
            }

            // Quick Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickStatCard(
                    icon = Icons.Default.CardMembership,
                    label = "Registration",
                    value = "TCM/2023/5678",
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.weight(1f)
                )

                QuickStatCard(
                    icon = Icons.Default.School,
                    label = "Experience",
                    value = "5 Years",
                    color = Color(0xFF43A047),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            // Personal Information Section
            SectionTitle("Personal Information")

            ProfileInfoCard(
                icon = Icons.Default.Person,
                label = "Full Name",
                value = "John Banda",
                onClick = { viewModel.onSectionSelected("name") }
            )

            ProfileInfoCard(
                icon = Icons.Default.Badge,
                label = "National ID",
                value = "MWI 1234567890123"
            )

            ProfileInfoCard(
                icon = Icons.Default.CalendarToday,
                label = "Date of Birth",
                value = "15 January 1990"
            )

            ProfileInfoCard(
                icon = Icons.Default.Wc,
                label = "Gender",
                value = "Male"
            )

            Spacer(Modifier.height(16.dp))

            // Contact Information
            SectionTitle("Contact Information")

            ProfileInfoCard(
                icon = Icons.Default.Email,
                label = "Email",
                value = "john.banda@tcm.mw",
                onClick = { viewModel.onSectionSelected("email") }
            )

            ProfileInfoCard(
                icon = Icons.Default.Phone,
                label = "Phone Number",
                value = "+265 888 123 456",
                onClick = { viewModel.onSectionSelected("phone") }
            )

            ProfileInfoCard(
                icon = Icons.Default.LocationOn,
                label = "District",
                value = "Blantyre"
            )

            ProfileInfoCard(
                icon = Icons.Default.Home,
                label = "Traditional Authority",
                value = "Kapeni"
            )

            Spacer(Modifier.height(16.dp))

            // Education
            SectionTitle("Education Qualifications")

            ProfileInfoCard(
                icon = Icons.Default.School,
                label = "MSCE",
                value = "2008"
            )

            ProfileInfoCard(
                icon = Icons.Default.MenuBook,
                label = "Teaching Diploma",
                value = "Diploma in Primary Education (2012)"
            )

            ProfileInfoCard(
                icon = Icons.Default.WorkspacePremium,
                label = "Degree",
                value = "Bachelor of Education (2020)"
            )

            Spacer(Modifier.height(16.dp))

            // Employment
            SectionTitle("Employment Information")

            ProfileInfoCard(
                icon = Icons.Default.Business,
                label = "Current School",
                value = "Blantyre Primary School"
            )

            ProfileInfoCard(
                icon = Icons.Default.LocationCity,
                label = "School District",
                value = "Blantyre"
            )

            ProfileInfoCard(
                icon = Icons.Default.Work,
                label = "Employment Type",
                value = "Government"
            )

            ProfileInfoCard(
                icon = Icons.Default.Timer,
                label = "Years of Experience",
                value = "5 Years"
            )

            Spacer(Modifier.height(24.dp))

            // Settings Section
            SectionTitle("Settings")

            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                subtitle = "Manage notification preferences",
                onClick = { }
            )

            SettingsItem(
                icon = Icons.Default.Security,
                title = "Change Password",
                subtitle = "Update your account password",
                onClick = { }
            )

            SettingsItem(
                icon = Icons.Default.Language,
                title = "Language",
                subtitle = "English",
                onClick = { }
            )

            SettingsItem(
                icon = Icons.Default.Help,
                title = "Help & Support",
                subtitle = "Get help or contact support",
                onClick = { }
            )

            SettingsItem(
                icon = Icons.Default.Info,
                title = "About",
                subtitle = "App version 1.0.0",
                onClick = { }
            )

            Spacer(Modifier.height(16.dp))

            // Logout Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Logout,
                        null,
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        "Logout",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFE53935)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun QuickStatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun ProfileInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    label,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (onClick != null) {
                Icon(
                    Icons.Default.ChevronRight,
                    null,
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                null,
                tint = Color.Gray
            )
        }
    }
}