package mw.gov.tcm.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mw.gov.tcm.data.model.LicenseApplication
import mw.gov.tcm.data.datasource.LicenseApplicationDataSource
import mw.gov.tcm.ui.feature_application_status.Document
import mw.gov.tcm.ui.feature_application_status.StageStatus
import mw.gov.tcm.ui.feature_application_status.TimelineStage
import javax.inject.Inject

class ApplicationStatusRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val licenseApplicationDataSource: LicenseApplicationDataSource
) : ApplicationStatusRepository {

    override suspend fun getApplicationStatus(): Pair<List<TimelineStage>, List<Document>> {
        val application = getLicenseApplication()

        val stages = mapApplicationToStages(application)
        val documents = mapApplicationToDocuments(application)

        return Pair(stages, documents)
    }

    override suspend fun getLicenseApplication(): LicenseApplication? {
        return licenseApplicationDataSource.getLicenseApplication()
    }

    private fun mapApplicationToStages(application: LicenseApplication?): List<TimelineStage> {
        if (application == null) return emptyList() // Or return a default state

        // This is a simplified mapping logic. You can expand this based on your app's needs.
        return listOf(
            TimelineStage(
                "Application Submitted",
                "Your application has been received",
                application.submissionDate.toString(), // Format this date as needed
                Icons.Default.CheckCircle,
                if (application.status >= "Submitted") StageStatus.COMPLETED else StageStatus.PENDING
            ),
            TimelineStage(
                "Initial Review",
                "Application under initial review",
                if (application.status == "In Review") "In Progress" else "Pending",
                Icons.Default.Assignment,
                if (application.status == "In Review") StageStatus.IN_PROGRESS else if (application.status > "In Review") StageStatus.COMPLETED else StageStatus.PENDING
            ),
            TimelineStage(
                "Document Verification",
                "Verifying uploaded documents",
                if (application.status == "Document Verification") "In Progress" else "Pending",
                Icons.Default.Description,
                if (application.status == "Document Verification") StageStatus.IN_PROGRESS else if (application.status > "Document Verification") StageStatus.COMPLETED else StageStatus.PENDING
            ),
            TimelineStage(
                "Payment Processing",
                "Awaiting payment confirmation",
                if (application.status == "Payment Processing") "In Progress" else "Pending",
                Icons.Default.Payment,
                if (application.status == "Payment Processing") StageStatus.IN_PROGRESS else if (application.status > "Payment Processing") StageStatus.COMPLETED else StageStatus.PENDING
            ),
            TimelineStage(
                "Approval & License Issue",
                "Final approval and license generation",
                if (application.status == "Approved") "Completed" else "Pending",
                Icons.Default.CardMembership,
                if (application.status == "Approved") StageStatus.COMPLETED else StageStatus.PENDING
            )
        )
    }

    private fun mapApplicationToDocuments(application: LicenseApplication?): List<Document> {
        if (application == null) return emptyList()

        // This assumes your LicenseApplication data class has a list of documents or fields for them.
        // You would replace these with actual fields from your LicenseApplication.
        return listOf(
            Document("Passport Photo", application.passportPhotoUrl.isNotBlank(), if (application.passportPhotoUrl.isNotBlank()) "Verified" else "Missing"),
            Document("National ID", application.nationalIdUrl.isNotBlank(), if (application.nationalIdUrl.isNotBlank()) "Verified" else "Missing"),
            Document("MSCE Certificate", application.msceCertificateUrl.isNotBlank(), if (application.msceCertificateUrl.isNotBlank()) "Verified" else "Missing"),
            Document("Teaching Diploma", application.teachingDiplomaUrl.isNotBlank(), if (application.teachingDiplomaUrl.isNotBlank()) "Verified" else "Missing"),
            Document("Police Report", application.policeReportUrl.isNotBlank(), if (application.policeReportUrl.isNotBlank()) "Verified" else "Missing")
        )
    }
}