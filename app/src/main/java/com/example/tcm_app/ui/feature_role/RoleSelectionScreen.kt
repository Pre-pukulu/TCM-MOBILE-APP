package com.example.tcm_app.ui.feature_role

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tcm_app.navigation.ObserveSingleFireNavigation
import com.example.tcm_app.navigation.Screen

@Composable
fun RoleSelectionScreen(
    navController: NavController,
    viewModel: RoleSelectionViewModel = viewModel()
) {
    val selectedRole by viewModel.selectedRole

    viewModel.navigationEvent.ObserveSingleFireNavigation { navigation ->
        when (navigation) {
            RoleSelectionNavigation.NavigateToStudentLogin -> navController.navigate(Screen.StudentLogin.route)
            RoleSelectionNavigation.NavigateToTeacherLogin -> navController.navigate(Screen.Login.route)
        }
    }

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    val scaleStudent = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleStudent"
    )
    val scaleTeacher = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleTeacher"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E88E5),
                        Color(0xFF1565C0)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))

            // TCM Logo
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TCM",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5)
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Teachers Council",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "of Malawi",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(48.dp))

            Text(
                text = "Welcome! Who are you?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Select your role to continue",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(48.dp))

            // Student Card
            RoleCard(
                icon = Icons.Default.School,
                title = "I'm a Student",
                subtitle = "Teacher trainee seeking indexing",
                description = "Register as a student teacher for indexing with TCM",
                gradient = listOf(Color(0xFF4CAF50), Color(0xFF388E3C)),
                isSelected = selectedRole == UserRole.STUDENT,
                onClick = { viewModel.onRoleSelected(UserRole.STUDENT) },
                modifier = Modifier.scale(scaleStudent.value)
            )

            Spacer(Modifier.height(20.dp))

            // Teacher Card
            RoleCard(
                icon = Icons.Default.Person,
                title = "I'm a Teacher",
                subtitle = "Qualified teacher seeking registration",
                description = "Register, manage license, and track CPD activities",
                gradient = listOf(Color(0xFF2196F3), Color(0xFF1976D2)),
                isSelected = selectedRole == UserRole.TEACHER,
                onClick = { viewModel.onRoleSelected(UserRole.TEACHER) },
                modifier = Modifier.scale(scaleTeacher.value)
            )

            Spacer(Modifier.weight(1f))

            // Continue Button
            Button(
                onClick = viewModel::onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = selectedRole != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF1E88E5),
                    disabledContainerColor = Color.White.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = if (selectedRole != null) "Continue" else "Select a role to continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                if (selectedRole != null) {
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, null)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Public Verification Link
            TextButton(
                onClick = {
                    navController.navigate(Screen.TeacherVerification.route)
                }
            ) {
                Icon(
                    Icons.Default.VerifiedUser,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Verify Teacher Registration",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun RoleCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    description: String,
    gradient: List<Color>,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 12.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box {
            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            Brush.horizontalGradient(gradient)
                        )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            Brush.linearGradient(gradient),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.width(16.dp))

                // Content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1B1F)
                    )
                    Text(
                        subtitle,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = gradient[0],
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Selection indicator
                if (isSelected) {
                    Icon(
                        Icons.Default.CheckCircle,
                        null,
                        tint = gradient[0],
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Icon(
                        Icons.Default.RadioButtonUnchecked,
                        null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

enum class UserRole {
    STUDENT, TEACHER
}