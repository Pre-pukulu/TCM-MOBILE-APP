package com.example.tcm_app.ui.feature_cpd_tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun CPDTrackingScreen(
    navController: NavController,
    viewModel: CPDTrackingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            CPDTrackingNavigation.Profile -> navController.navigate(Screen.Profile.route)
            CPDTrackingNavigation.Settings -> navController.navigate(Screen.Settings.route)
            CPDTrackingNavigation.Login -> navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CPD Tracking") },
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = viewModel::onAddActivityClicked,
                icon = { Icon(Icons.Default.Add, null) },
                text = { Text("Add Activity") },
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Year Selector
            item {
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Academic Year",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { viewModel.onYearChanged(-1) }) {
                                Icon(Icons.Default.ArrowBack, null)
                            }
                            Text(
                                "${uiState.selectedYear}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            IconButton(onClick = { viewModel.onYearChanged(1) }) {
                                Icon(Icons.Default.ArrowForward, null)
                            }
                        }
                    }
                }
            }

            // Progress Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(20.dp),
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
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text(
                                    "CPD Points Progress",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "${uiState.selectedYear} Academic Year",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(30.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "${(uiState.progress * 100).toInt()}%",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Current: ${uiState.totalPoints.toInt()}",
                                fontSize = 14.sp
                            )
                            Text(
                                "Target: ${uiState.requiredPoints.toInt()}",
                                fontSize = 14.sp
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = uiState.progress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = if (uiState.progress >= 1.0) Color(0xFF43A047) else MaterialTheme.colorScheme.primary,
                            trackColor = Color.LightGray
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            if (uiState.progress >= 1.0)
                                "✓ Compliant - Well done!"
                            else
                                "${(uiState.requiredPoints - uiState.totalPoints).toInt()} points needed for compliance",
                            fontSize = 13.sp,
                            color = if (uiState.progress >= 1.0) Color(0xFF43A047) else Color.Gray,
                            fontWeight = if (uiState.progress >= 1.0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

            // Statistics Cards
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Activities",
                        value = "${uiState.cpdActivities.size}",
                        icon = Icons.Default.Assignment,
                        color = Color(0xFF1E88E5),
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Total Hours",
                        value = "${uiState.cpdActivities.sumOf { it.hours }.toInt()}",
                        icon = Icons.Default.Timer,
                        color = Color(0xFF43A047),
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Pending",
                        value = "${uiState.cpdActivities.count { it.status == "Pending" }}",
                        icon = Icons.Default.HourglassEmpty,
                        color = Color(0xFFFF9800),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(24.dp))
            }

            // Section Header
            item {
                Text(
                    "My Activities",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Activities List
            items(uiState.cpdActivities) { activity ->
                CPDActivityCard(activity)
                Spacer(Modifier.height(12.dp))
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }

    // Add Activity Dialog
    if (uiState.showAddDialog) {
        AddCPDActivityDialog(
            onDismiss = viewModel::onDismissDialog,
            onAdd = viewModel::onAddActivity
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
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
                value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                title,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CPDActivityCard(activity: CPDActivity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        activity.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        activity.type,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = activity.statusColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        activity.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = activity.statusColor
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(Icons.Default.Business, activity.provider)
                InfoChip(Icons.Default.CalendarToday, activity.date)
            }

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "${activity.hours.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Hours",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(Color.LightGray)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        if (activity.points > 0) "${activity.points.toInt()}" else "-",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (activity.points > 0) Color(0xFF43A047) else Color.Gray
                    )
                    Text(
                        "Points",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            null,
            modifier = Modifier.size(16.dp),
            tint = Color.Gray
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCPDActivityDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Double) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Workshop") }
    var hours by remember { mutableStateOf("") }
    var typeExpanded by remember { mutableStateOf(false) }

    val types = listOf("Workshop", "Seminar", "Conference", "Online Course", "Mentorship", "Other")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add CPD Activity") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Activity Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = { typeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Type") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = typeExpanded,
                        onDismissRequest = { typeExpanded = false }
                    ) {
                        types.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    typeExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = hours,
                    onValueChange = { hours = it },
                    label = { Text("Hours") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && hours.isNotBlank()) {
                        onAdd(title, selectedType, hours.toDoubleOrNull() ?: 0.0)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
