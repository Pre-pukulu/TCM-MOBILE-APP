package com.example.tcm_app.ui.feature_application_status

import androidx.compose.foundation.background
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
import com.example.tcm_app.navigation.ObserveSingleFireNavigation
import com.example.tcm_app.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationStatusScreen(
    navController: NavController,
    viewModel: ApplicationStatusViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            ApplicationStatusNavigation.Profile -> navController.navigate(Screen.Profile.route)
            ApplicationStatusNavigation.Settings -> navController.navigate(Screen.Settings.route)
            ApplicationStatusNavigation.Login -> navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Application Status") },
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
            // Application Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Application #TCM2024-001234",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Teacher Registration",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFF9800).copy(alpha = 0.2f)
                        ) {
                            Text(
                                "In Progress",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9800)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoItem("Submitted", "15 Nov 2024")
                        InfoItem("Est. Completion", "15 Dec 2024")
                    }
                }
            }

            // Progress Overview
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
                        "Overall Progress",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LinearProgressIndicator(
                            progress = (uiState.currentStage + 1) / 5f,
                            modifier = Modifier
                                .weight(1f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            color = Color(0xFFFF9800)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "${((uiState.currentStage + 1) / 5f * 100).toInt()}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF9800)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Step ${uiState.currentStage + 1} of 5",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Timeline
            Text(
                "Application Timeline",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            uiState.timelineStages.forEachIndexed { index, stage ->
                TimelineItem(
                    stage = stage,
                    isLast = index == uiState.timelineStages.size - 1
                )
            }

            Spacer(Modifier.height(24.dp))

            // Action Required Card
            if (uiState.currentStage == 2) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF3E0)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
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
                                "Action Required",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Please upload a clear copy of your Police Report to continue.",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800)
                            )
                        ) {
                            Icon(Icons.Default.Upload, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Upload Document")
                        }
                    }
                }
            }

            // Documents Status
            Text(
                "Submitted Documents",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            uiState.documents.forEach { doc ->
                DocumentStatusCard(doc)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(24.dp))

            // Contact Support
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.SupportAgent,
                        null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Need Help?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Contact support for assistance",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Button(
                        onClick = { }
                    ) {
                        Text("Contact")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TimelineItem(
    stage: TimelineStage,
    isLast: Boolean
) {
    val color = when (stage.status) {
        StageStatus.COMPLETED -> Color(0xFF43A047)
        StageStatus.IN_PROGRESS -> Color(0xFFFF9800)
        StageStatus.PENDING -> Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Timeline indicator
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    stage.icon,
                    null,
                    tint = color,
                    modifier = Modifier.size(28.dp)
                )
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(color.copy(alpha = 0.3f))
                )
            }
        }

        Spacer(Modifier.width(16.dp))

        // Stage content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (!isLast) 16.dp else 0.dp)
        ) {
            Text(
                stage.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                stage.description,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Schedule,
                    null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.Gray
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    stage.timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DocumentStatusCard(document: Document) {
    val statusColor = when (document.status) {
        "Verified" -> Color(0xFF43A047)
        "Under Review" -> Color(0xFFFF9800)
        "Missing" -> Color(0xFFE53935)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                when (document.status) {
                    "Verified" -> Icons.Default.CheckCircle
                    "Under Review" -> Icons.Default.HourglassEmpty
                    "Missing" -> Icons.Default.Error
                    else -> Icons.Default.Description
                },
                null,
                tint = statusColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    document.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    if (document.uploaded) "Uploaded" else "Not uploaded",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = statusColor.copy(alpha = 0.1f)
            ) {
                Text(
                    document.status,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
        }
    }
}