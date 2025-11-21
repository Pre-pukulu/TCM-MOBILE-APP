package mw.gov.tcm.data.model

import java.util.Date

data class LicenseApplication(
    val id: String = "",
    val userId: String = "",
    val submissionDate: Date = Date(),
    val status: String = "", // e.g., "Submitted", "In Review", "Approved"
    val passportPhotoUrl: String = "",
    val nationalIdUrl: String = "",
    val msceCertificateUrl: String = "",
    val teachingDiplomaUrl: String = "",
    val policeReportUrl: String = ""
)