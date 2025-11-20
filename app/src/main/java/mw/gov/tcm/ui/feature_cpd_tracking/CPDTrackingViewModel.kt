package mw.gov.tcm.ui.feature_cpd_tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mw.gov.tcm.data.model.CpdActivity
import javax.inject.Inject

data class CpdTrackingUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val activities: List<CpdActivity> = emptyList()
)

@HiltViewModel
class CpdTrackingViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(CpdTrackingUiState())
    val uiState = _uiState.asStateFlow()

    private val userId: String
        get() = auth.currentUser?.uid ?: ""

    init {
        fetchCpdActivities()
    }

    private fun fetchCpdActivities() {
        if (userId.isEmpty()) {
            _uiState.update { it.copy(isLoading = false, error = "User not logged in.") }
            return
        }

        db.collection("users").document(userId).collection("cpd_activities")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    _uiState.update { it.copy(isLoading = false, error = "Failed to load activities: ${e.message}") }
                    return@addSnapshotListener
                }

                val activities = snapshots?.toObjects(CpdActivity::class.java) ?: emptyList()
                val validActivities = activities.filter { it.documentId.isNotEmpty() }
                _uiState.update { it.copy(isLoading = false, activities = validActivities, error = null) }
            }
    }

    fun addCpdActivity(activity: CpdActivity) {
        if (userId.isEmpty()) {
            // Handle error, maybe show a toast or a snackbar
            return
        }

        db.collection("users").document(userId).collection("cpd_activities")
            .add(activity)
            .addOnSuccessListener {
                // Optionally refresh or rely on snapshot listener
            }
            .addOnFailureListener { e ->
                _uiState.update { it.copy(error = "Failed to add activity: ${e.message}") }
            }
    }
}