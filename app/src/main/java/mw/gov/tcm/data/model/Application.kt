package mw.gov.tcm.data.model

import com.google.firebase.firestore.DocumentId

data class Application(
    @DocumentId
    val id: String = "",
    val fullName: String = "",
    val tcmNumber: String = "",
    val type: String = ""
)