package mw.gov.tcm.data.datasource

import mw.gov.tcm.data.model.LicenseApplication

interface LicenseApplicationDataSource {
    suspend fun getLicenseApplication(): LicenseApplication?
}