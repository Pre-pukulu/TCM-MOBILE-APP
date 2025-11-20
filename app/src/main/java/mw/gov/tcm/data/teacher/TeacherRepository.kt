package mw.gov.tcm.data.teacher

import mw.gov.tcm.data.model.Application
import mw.gov.tcm.data.model.License
import mw.gov.tcm.data.model.Teacher
import mw.gov.tcm.ui.feature_teacher_verification.TeacherSearchResult

interface TeacherRepository {
    suspend fun getTeacher(teacherId: String): Teacher?
    suspend fun submitLicenseApplication(application: Application)
    suspend fun getLicense(teacherId: String): License?
    suspend fun saveTeacher(teacher: Teacher)
    suspend fun verifyTeacher(query: String, searchType: String): TeacherSearchResult?
    suspend fun getApplication(teacherId: String): Application?
}