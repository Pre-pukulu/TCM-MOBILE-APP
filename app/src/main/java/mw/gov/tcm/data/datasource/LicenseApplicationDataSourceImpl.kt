package mw.gov.tcm.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mw.gov.tcm.data.model.LicenseApplication
import javax.inject.Inject

class LicenseApplicationDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : LicenseApplicationDataSource {

    override suspend fun getLicenseApplication(): LicenseApplication? {
        val userId = auth.currentUser?.uid ?: return null

        return try {
            val document = firestore.collection("licenseApplications")
                .whereEqualTo("userId", userId)
                .limit(1)
                .get()
                .await()

            document.documents.firstOrNull()?.toObject(LicenseApplication::class.java)
        } catch (e: Exception) {
            // Handle exceptions, e.g., logging
            null
        }
    }
}