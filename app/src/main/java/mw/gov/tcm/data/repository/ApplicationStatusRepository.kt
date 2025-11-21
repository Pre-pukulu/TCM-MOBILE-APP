package mw.gov.tcm.data.repository

import mw.gov.tcm.data.model.LicenseApplication
import mw.gov.tcm.ui.feature_application_status.TimelineStage
import mw.gov.tcm.ui.feature_application_status.Document

interface ApplicationStatusRepository {
    suspend fun getApplicationStatus(): Pair<List<TimelineStage>, List<Document>>
    suspend fun getLicenseApplication(): LicenseApplication?
}