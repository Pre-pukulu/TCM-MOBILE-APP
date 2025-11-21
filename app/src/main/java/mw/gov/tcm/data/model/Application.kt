package mw.gov.tcm.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Application(
    @DocumentId
    val id: String = "",
    val teacherId: String = "",
    val fullName: String = "",
    val tcmNumber: String = "",
    val type: String = "",
    val status: String = "",
    val applicationDate: Date = Date(),
    val estimatedCompletionDate: Date = Date()
)