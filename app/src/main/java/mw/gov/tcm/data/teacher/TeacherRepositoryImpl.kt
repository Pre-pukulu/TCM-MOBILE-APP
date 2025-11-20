package mw.gov.tcm.data.teacher

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mw.gov.tcm.data.model.Application
import mw.gov.tcm.data.model.License
import mw.gov.tcm.data.model.Teacher
import mw.gov.tcm.ui.feature_teacher_verification.TeacherSearchResult
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TeacherRepository {

    override suspend fun getTeacher(teacherId: String): Teacher? {
        return try {
            firestore.collection("teachers").document(teacherId).get().await()
                .toObject(Teacher::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun submitLicenseApplication(application: Application) {
        firestore.collection("applications").add(application).await()
    }

    override suspend fun getLicense(teacherId: String): License? {
        return try {
            firestore.collection("licenses").whereEqualTo("teacherId", teacherId).get().await()
                .toObjects(License::class.java).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveTeacher(teacher: Teacher) {
        firestore.collection("teachers").document(teacher.id).set(teacher).await()
    }

    override suspend fun verifyTeacher(query: String, searchType: String): TeacherSearchResult? {
        val field = when (searchType) {
            "Registration Number" -> "registrationNumber"
            "National ID" -> "nationalId"
            "Full Name" -> "firstName"
            else -> return null
        }

        return try {
            val snapshot = firestore.collection("teachers")
                .whereEqualTo(field, query)
                .limit(1)
                .get()
                .await()

            if (snapshot.isEmpty) {
                TeacherSearchResult(found = false)
            } else {
                val teacher = snapshot.documents.first().toObject(Teacher::class.java)
                if (teacher != null) {
                    val license = getLicense(teacher.id)
                    TeacherSearchResult(
                        found = true,
                        name = "${teacher.firstName} ${teacher.lastName}",
                        registrationNumber = "N/A",
                        nationalId = teacher.nationalId,
                        status = if (license != null) "Active" else "Inactive",
                        licenseExpiry = license?.expiryDate ?: "N/A",
                        school = "N/A",
                        district = teacher.district,
                        qualifications = "N/A",
                        issueDate = license?.dateOfIssue ?: "N/A"
                    )
                } else {
                    TeacherSearchResult(found = false)
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getApplications(teacherId: String): List<Application> {
        return try {
            firestore.collection("applications").whereEqualTo("teacherId", teacherId).get().await()
                .toObjects(Application::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}