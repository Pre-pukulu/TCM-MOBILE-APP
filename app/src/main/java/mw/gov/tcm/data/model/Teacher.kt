package mw.gov.tcm.data.model

import androidx.annotation.Keep
import com.google.firebase.firestore.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class Teacher(
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val nationalId: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val phone: String = "",
    val email: String = "",
    val district: String = "",
    val traditionalAuthority: String = "",
    val village: String = "",
    val msceYear: String = "",
    val diploma: String = "",
    val diplomaYear: String = "",
    val degree: String = "",
    val degreeYear: String = ""
)