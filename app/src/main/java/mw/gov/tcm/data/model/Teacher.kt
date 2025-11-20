package mw.gov.tcm.data.model

import com.google.firebase.firestore.DocumentId

data class Teacher(
    @DocumentId
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val registrationStep: Int = 0,
    val middleName: String = "",
    val nationalId: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val phone: String = "",
    val district: String = "",
    val traditionalAuthority: String = "",
    val village: String = "",
    val msceYear: String = "",
    val diploma: String = "",
    val diplomaYear: String = "",
    val degree: String = "",
    val degreeYear: String = ""
)