package mw.gov.tcm.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegistrationProgressRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val registrationStepKey = intPreferencesKey("registration_step")

    fun getRegistrationStep(): Flow<Int> {
        return dataStore.data.map {
            it[registrationStepKey] ?: 0
        }
    }

    suspend fun setRegistrationStep(step: Int) {
        dataStore.edit {
            it[registrationStepKey] = step
        }
    }
}