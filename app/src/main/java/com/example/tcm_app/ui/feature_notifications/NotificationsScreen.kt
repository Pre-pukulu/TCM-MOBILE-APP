package com.example.tcm_app.ui.feature_notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            NotificationsNavigation.Settings -> navController.navigate(Screen.Settings.route)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Notifications")
                        if (uiState.unreadCount > 0) {
                            Text(
                                "${uiState.unreadCount} unread",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                },
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
                            text = { Text("Mark all as read") },
                            onClick = viewModel::onMarkAllAsReadClick,
                            leadingIcon = { Icon(Icons.Default.DoneAll, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Clear all") },
                            onClick = viewModel::onClearAllClick,
                            leadingIcon = { Icon(Icons.Default.Delete, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = viewModel::onSettingsClick,
                            leadingIcon = { Icon(Icons.Default.Settings, null) }
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
        ) {
            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedFilter == "All",
                    onClick = { viewModel.onFilterSelected("All") },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == "Unread",
                    onClick = { viewModel.onFilterSelected("Unread") },
                    label = { Text("Unread (${uiState.unreadCount})") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == "CPD",
                    onClick = { viewModel.onFilterSelected("CPD") },
                    label = { Text("CPD") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == "License",
                    onClick = { viewModel.onFilterSelected("License") },
                    label = { Text("License") }
                )
            }

            // Notifications List
            if (uiState.notifications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            null,
                            modifier = Modifier.size(80.dp),
                            tint = Color.Gray.copy(alpha = 0.5f)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "No notifications",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        Text(
                            "You're all caught up!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(uiState.filteredNotifications) { notification ->
                        NotificationCard(
                            notification = notification,
                            onClick = { viewModel.onNotificationClicked(notification) },
                            onDismiss = { viewModel.onNotificationDismissed(notification) }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    
                    item {
                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCard(
    notification: NotificationItem,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val (icon, iconColor) = when (notification.type) {
        NotificationType.LICENSE -> Icons.Default.CardMembership to Color(0xFF1E88E5)
        NotificationType.CPD -> Icons.Default.School to Color(0xFF43A047)
        NotificationType.APPLICATION -> Icons.Default.Assignment to Color(0xFF9C27B0)
        NotificationType.PAYMENT -> Icons.Default.Payment to Color(0xFFFF9800)
        NotificationType.SYSTEM -> Icons.Default.Info to Color(0xFF757575)
        NotificationType.GENERAL -> Icons.Default.Notifications to Color(0xFF1E88E5)
    }

    var showDismiss by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) 
                Color.White else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(iconColor.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            notification.title,
                            fontSize = 15.sp,
                            fontWeight = if (notification.isRead) 
                                FontWeight.Medium else FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            notification.message,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
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
                                notification.time,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            
                            if (!notification.isRead) {
                                Spacer(Modifier.width(12.dp))
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary
                                ) {
                                    Box(modifier = Modifier.size(8.dp))
                                }
                            }
                        }
                    }

                    // Dismiss button
                    IconButton(
                        onClick = { showDismiss = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            "Dismiss",
                            modifier = Modifier.size(18.dp),
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }

    // Dismiss confirmation dialog
    if (showDismiss) {
        AlertDialog(
            onDismissRequest = { showDismiss = false },
            icon = { Icon(Icons.Default.Delete, null) },
            title = { Text("Remove notification?") },
            text = { Text("This notification will be permanently deleted.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                        showDismiss = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935)
                    )
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDismiss = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}