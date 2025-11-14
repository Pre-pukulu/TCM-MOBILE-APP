package mw.gov.tcm.data.teacher

import kotlinx.coroutines.flow.Flow
import mw.gov.tcm.data.Result
import mw.gov.tcm.data.model.Application
import mw.gov.tcm.data.model.CpdActivity
import mw.gov.tcm.data.model.License
import mw.gov.tcm.data.model.Teacher

interface TeacherRepository {
    fun saveTeacher(teacher: Teacher): Flow<Result<Unit>>
    fun getTeacher(): Flow<Result<Teacher?>>
    fun getLicense(): Flow<Result<License?>>
    fun addCpdActivity(activity: CpdActivity): Flow<Result<Unit>>
    fun getCpdActivities(): Flow<Result<List<CpdActivity>>>
    fun getApplications(): Flow<Result<List<Application>>>
    fun deleteCpdActivity(documentId: String): Flow<Result<Unit>>
    fun submitLicenseApplication(application: Application): Flow<Result<Unit>>
}