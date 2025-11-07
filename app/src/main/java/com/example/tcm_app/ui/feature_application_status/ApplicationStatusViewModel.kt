package com.example.tcm_app.ui.feature_application_status

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class StageStatus {
    COMPLETED, IN_PROGRESS, PENDING
}

data class TimelineStage(
    val title: String,
    val description: String,
    val timestamp: String,
    val icon: ImageVector,
    val status: StageStatus
)

data class Document(
    val name: String,
    val uploaded: Boolean,
    val status: String
)

data class ApplicationStatusState(
    val currentStage: Int = 2, // 0-indexed: 0=Submitted, 1=Under Review, 2=Document Verification, 3=Payment, 4=Approved
    val timelineStages: List<TimelineStage> = listOf(
        TimelineStage(
            "Application Submitted",
            "Your application has been received",
            "15 Nov 2024, 10:30 AM",
            Icons.Default.CheckCircle,
            StageStatus.COMPLETED
        ),
        TimelineStage(
            "Initial Review",
            "Application under initial review",
            "16 Nov 2024, 2:15 PM",
            Icons.Default.Assignment,
            StageStatus.COMPLETED
        ),
        TimelineStage(
            "Document Verification",
            "Verifying uploaded documents",
            "In Progress",
            Icons.Default.Description,
            StageStatus.IN_PROGRESS
        ),
        TimelineStage(
            "Payment Processing",
            "Awaiting payment confirmation",
            "Pending",
            Icons.Default.Payment,
            StageStatus.PENDING
        ),
        TimelineStage(
            "Approval & License Issue",
            "Final approval and license generation",
            "Pending",
            Icons.Default.CardMembership,
            StageStatus.PENDING
        )
    ),
    val documents: List<Document> = listOf(
        Document("Passport Photo", true, "Verified"),
        Document("National ID", true, "Verified"),
        Document("MSCE Certificate", true, "Verified"),
        Document("Teaching Diploma", true, "Under Review"),
        Document("Police Report", false, "Missing")
    )
)

class ApplicationStatusViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ApplicationStatusState())
    val uiState = _uiState.asStateFlow()
}