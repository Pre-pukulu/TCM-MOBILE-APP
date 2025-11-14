package mw.gov.tcm.data.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
data class License(
    val licenseNumber: String = "",
    val issueDate: Timestamp? = null,
    val expiryDate: Timestamp? = null,
    val status: String = ""
)