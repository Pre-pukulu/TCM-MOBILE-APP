package mw.gov.tcm.data.model

import com.google.firebase.Timestamp

data class Application(
    val type: String = "",
    val status: String = "Pending",
    val dateSubmitted: Timestamp? = null,
    val lastUpdated: Timestamp? = null,
    val fullName: String = "",
    val tcmNumber: String = ""
)