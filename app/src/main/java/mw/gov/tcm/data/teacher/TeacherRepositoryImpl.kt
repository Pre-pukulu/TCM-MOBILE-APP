package mw.gov.tcm.data.teacher

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mw.gov.tcm.data.Result
import mw.gov.tcm.data.model.Application
import mw.gov.tcm.data.model.CpdActivity
import mw.gov.tcm.data.model.License
import mw.gov.tcm.data.model.Teacher
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : TeacherRepository {
    override fun saveTeacher(teacher: Teacher): Flow<Result<Unit>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                firestore.collection("teachers")
                    .document(currentUser.uid)
                    .set(teacher)
                    .await()
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getTeacher(): Flow<Result<Teacher?>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val document = firestore.collection("teachers").document(currentUser.uid).get().await()
                val teacher = document.toObject<Teacher>()
                emit(Result.Success(teacher))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getLicense(): Flow<Result<License?>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val document = firestore.collection("licenses").document(currentUser.uid).get().await()
                val license = document.toObject<License>()
                emit(Result.Success(license))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun addCpdActivity(activity: CpdActivity): Flow<Result<Unit>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                firestore.collection("teachers")
                    .document(currentUser.uid)
                    .collection("cpd-activities")
                    .add(activity)
                    .await()
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getCpdActivities(): Flow<Result<List<CpdActivity>>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val querySnapshot = firestore.collection("teachers")
                    .document(currentUser.uid)
                    .collection("cpd-activities")
                    .get()
                    .await()
                val activities = querySnapshot.toObjects(CpdActivity::class.java)
                emit(Result.Success(activities))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getApplications(): Flow<Result<List<Application>>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val querySnapshot = firestore.collection("teachers")
                    .document(currentUser.uid)
                    .collection("applications")
                    .get()
                    .await()
                val applications = querySnapshot.toObjects(Application::class.java)
                emit(Result.Success(applications))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun deleteCpdActivity(documentId: String): Flow<Result<Unit>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                firestore.collection("teachers")
                    .document(currentUser.uid)
                    .collection("cpd-activities")
                    .document(documentId)
                    .delete()
                    .await()
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun submitLicenseApplication(application: Application): Flow<Result<Unit>> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                firestore.collection("teachers")
                    .document(currentUser.uid)
                    .collection("applications")
                    .add(application)
                    .await()
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error(Exception("User not logged in")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}