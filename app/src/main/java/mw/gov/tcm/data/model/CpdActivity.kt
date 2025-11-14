package mw.gov.tcm.data.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class CpdActivity(
    val title: String = "",
    val description: String = "",
    val date: Timestamp? = null,
    val credits: Int = 0,
    val hours: Int = 0,
    val status: String = "",
    val category: String = "",
    val provider: String = ""
) {
    @DocumentId
    var documentId: String = ""
}