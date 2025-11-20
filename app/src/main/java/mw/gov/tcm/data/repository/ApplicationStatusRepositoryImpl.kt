package mw.gov.tcm.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mw.gov.tcm.ui.feature_application_status.Document
import mw.gov.tcm.ui.feature_application_status.StageStatus
import mw.gov.tcm.ui.feature_application_status.TimelineStage
import javax.inject.Inject

class ApplicationStatusRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ApplicationStatusRepository {

    override suspend fun getApplicationStatus(): Pair<List<TimelineStage>, List<Document>> {
        val userId = auth.currentUser?.uid ?: return Pair(emptyList(), emptyList())

        // In a real app, you might fetch the application first to determine what to show.
        // For now, we will return the same dummy data regardless of the user.

        val stages = getDummyStages()
        val documents = getDummyDocuments()
        return Pair(stages, documents)
    }

    private fun getDummyStages(): List<TimelineStage> {
        return listOf(
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
        )
    }

    private fun getDummyDocuments(): List<Document> {
        return listOf(
            Document("Passport Photo", true, "Verified"),
            Document("National ID", true, "Verified"),
            Document("MSCE Certificate", true, "Verified"),
            Document("Teaching Diploma", true, "Under Review"),
            Document("Police Report", false, "Missing")
        )
    }
}