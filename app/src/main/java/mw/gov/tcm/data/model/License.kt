package mw.gov.tcm.data.model

import com.google.firebase.firestore.DocumentId

data class License(
    @DocumentId
    val id: String = "",
    val teacherId: String = "",
    val licenseNumber: String = "",
    val dateOfIssue: String = "",
    val expiryDate: String = "",
    val status: String = ""
)