package mw.gov.tcm.ui.feature_admin

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mw.gov.tcm.navigation.ObserveSingleFireNavigation
import mw.gov.tcm.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    navController: NavController,
    viewModel: AdminDashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation: AdminDashboardNavigation ->
        when (navigation) {
            AdminDashboardNavigation.Settings -> navController.navigate(Screen.Settings.route)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Admin Dashboard")
                        Text(
                            "TCM Management Portal",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
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
                            text = { Text("Export Data") },
                            onClick = { viewModel.onMenuToggled() },
                            leadingIcon = { Icon(Icons.Default.Download, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Reports") },
                            onClick = { viewModel.onMenuToggled() },
                            leadingIcon = { Icon(Icons.Default.Assessment, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                viewModel.onMenuToggled()
                                viewModel.onSettingsClick()
                            },
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
            // Statistics Cards
            LazyColumn {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Overview",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AdminStatCard(
                                title = "Total Teachers",
                                value = uiState.totalTeachers.toString(),
                                icon = Icons.Default.People,
                                color = Color(0xFF1E88E5),
                                modifier = Modifier.weight(1f)
                            )

                            AdminStatCard(
                                title = "Pending",
                                value = uiState.pendingApplications.toString(),
                                icon = Icons.Default.HourglassEmpty,
                                color = Color(0xFFFF9800),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AdminStatCard(
                                title = "In Review",
                                value = uiState.activeApplications.toString(),
                                icon = Icons.Default.Assignment,
                                color = Color(0xFF9C27B0),
                                modifier = Modifier.weight(1f)
                            )

                            AdminStatCard(
                                title = "Expiring Soon",
                                value = uiState.expiringSoon.toString(),
                                icon = Icons.Default.Warning,
                                color = Color(0xFFE53935),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Tabs
                item {
                    TabRow(
                        selectedTabIndex = uiState.selectedTab,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Tab(
                            selected = uiState.selectedTab == 0,
                            onClick = { viewModel.onTabSelected(0) },
                            text = { Text("Pending") }
                        )
                        Tab(
                            selected = uiState.selectedTab == 1,
                            onClick = { viewModel.onTabSelected(1) },
                            text = { Text("Approved") }
                        )
                        Tab(
                            selected = uiState.selectedTab == 2,
                            onClick = { viewModel.onTabSelected(2) },
                            text = { Text("All Teachers") }
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Content based on selected tab
                when (uiState.selectedTab) {
                    0 -> {
                        // Pending Applications
                        val pendingApps = listOf(
                            ApplicationItem(
                                "APP-2024-001",
                                "Mary Phiri",
                                "15 Nov 2024",
                                "Document Verification",
                                Color(0xFFFF9800)
                            ),
                            ApplicationItem(
                                "APP-2024-002",
                                "Peter Mwale",
                                "14 Nov 2024",
                                "Initial Review",
                                Color(0xFFFF9800)
                            ),
                            ApplicationItem(
                                "APP-2024-003",
                                "Grace Banda",
                                "13 Nov 2024",
                                "Payment Pending",
                                Color(0xFFFF9800)
                            )
                        )

                        items(pendingApps) { app ->
                            ApplicationCard(
                                application = app,
                                onApprove = { },
                                onReject = { },
                                onView = { }
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    1 -> {
                        // Approved Applications
                        val approvedApps = listOf(
                            ApplicationItem(
                                "APP-2024-098",
                                "John Banda",
                                "10 Nov 2024",
                                "Approved",
                                Color(0xFF43A047)
                            ),
                            ApplicationItem(
                                "APP-2024-097",
                                "Sarah Phiri",
                                "09 Nov 2024",
                                "Approved",
                                Color(0xFF43A047)
                            )
                        )

                        items(approvedApps) { app ->
                            ApplicationCard(
                                application = app,
                                isApproved = true,
                                onView = { }
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    2 -> {
                        // All Teachers
                        item {
                            SearchBar()
                        }

                        val teachers = listOf(
                            TeacherItem("John Banda", "TCM/2024/001234", "Active", "Blantyre"),
                            TeacherItem("Mary Phiri", "TCM/2024/001235", "Active", "Lilongwe"),
                            TeacherItem("Peter Mwale", "TCM/2024/001236", "Expired", "Mzuzu"),
                            TeacherItem("Grace Banda", "TCM/2024/001237", "Active", "Zomba")
                        )

                        items(teachers) { teacher ->
                            TeacherListCard(teacher)
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
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
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                value,
                fontSize = 28.sp,
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

data class ApplicationItem(
    val id: String,
    val teacherName: String,
    val date: String,
    val status: String,
    val statusColor: Color
)

@Composable
fun ApplicationCard(
    application: ApplicationItem,
    isApproved: Boolean = false,
    onApprove: (() -> Unit)? = null,
    onReject: (() -> Unit)? = null,
    onView: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onView),
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
                        application.teacherName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        application.id,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CalendarToday,
                            null,
                            modifier = Modifier.size(14.dp),
                            tint = Color.Gray
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            application.date,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = application.statusColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        application.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = application.statusColor
                    )
                }
            }

            if (!isApproved && onApprove != null && onReject != null) {
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFE53935)
                        )
                    ) {
                        Icon(Icons.Default.Close, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Reject")
                    }

                    Button(
                        onClick = onApprove,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF43A047)
                        )
                    ) {
                        Icon(Icons.Default.Check, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Approve")
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            placeholder = { Text("Search teachers...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

data class TeacherItem(
    val name: String,
    val registrationNumber: String,
    val status: String,
    val district: String
)

@Composable
fun TeacherListCard(teacher: TeacherItem) {
    val statusColor = if (teacher.status == "Active") Color(0xFF43A047) else Color(0xFFE53935)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { },
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
                    .size(50.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    teacher.name.split(" ")
                        .mapNotNull { it.firstOrNull() }
                        .take(2)
                        .joinToString(""),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    teacher.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    teacher.registrationNumber,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    teacher.district,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = statusColor.copy(alpha = 0.1f)
            ) {
                Text(
                    teacher.status,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
        }
    }
}