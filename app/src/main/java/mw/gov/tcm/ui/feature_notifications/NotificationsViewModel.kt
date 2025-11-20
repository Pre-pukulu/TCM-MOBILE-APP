package mw.gov.tcm.ui.feature_notifications

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import mw.gov.tcm.navigation.mutableSingleFireNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class NotificationsNavigation {
    object Settings : NotificationsNavigation()
}

enum class NotificationType {
    GENERAL, LICENSE, CPD, APPLICATION, PAYMENT, SYSTEM
}

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean
)

data class NotificationsState(
    val showMenu: Boolean = false,
    val selectedFilter: String = "All",
    val notifications: List<NotificationItem> = listOf(
        NotificationItem(
            "1",
            "License Renewal Reminder",
            "Your teaching license will expire in 30 days. Please renew to avoid interruption.",
            "2 hours ago",
            NotificationType.LICENSE,
            false
        ),
        NotificationItem(
            "2",
            "CPD Activity Approved",
            "Your CPD activity 'Modern Teaching Methods Workshop' has been approved. You earned 5 points.",
            "1 day ago",
            NotificationType.CPD,
            false
        ),
        NotificationItem(
            "3",
            "Application Status Update",
            "Your registration application is now under document verification.",
            "2 days ago",
            NotificationType.APPLICATION,
            true
        ),
        NotificationItem(
            "4",
            "Payment Confirmation",
            "We have received your registration payment of MK 25,000. Thank you!",
            "3 days ago",
            NotificationType.PAYMENT,
            true
        ),
        NotificationItem(
            "5",
            "System Maintenance Notice",
            "TCM portal will be under maintenance on Sunday from 2 AM to 6 AM.",
            "5 days ago",
            NotificationType.SYSTEM,
            true
        ),
        NotificationItem(
            "6",
            "CPD Compliance Reminder",
            "You need 5 more CPD points to meet the annual requirement.",
            "1 week ago",
            NotificationType.CPD,
            true
        ),
        NotificationItem(
            "7",
            "Profile Updated",
            "Your profile information has been successfully updated.",
            "1 week ago",
            NotificationType.GENERAL,
            true
        ),
        NotificationItem(
            "8",
            "Welcome to TCM Portal",
            "Thank you for registering with Teachers Council of Malawi. Complete your profile to get started.",
            "2 weeks ago",
            NotificationType.GENERAL,
            true
        )
    )
) {
    val unreadCount: Int
        get() = notifications.count { !it.isRead }

    val filteredNotifications: List<NotificationItem>
        get() = when (selectedFilter) {
            "Unread" -> notifications.filter { !it.isRead }
            "CPD" -> notifications.filter { it.type == NotificationType.CPD }
            "License" -> notifications.filter { it.type == NotificationType.LICENSE }
            else -> notifications
        }
}

class NotificationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = mutableSingleFireNavigation<NotificationsNavigation>()
    val navigationEvent = _navigationEvent

    fun onMenuToggled() {
        _uiState.update { it.copy(showMenu = !it.showMenu) }
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun onMarkAllAsReadClick() {
        _uiState.update { state ->
            state.copy(notifications = state.notifications.map { it.copy(isRead = true) })
        }
        onMenuToggled()
    }

    fun onClearAllClick() {
        _uiState.update { it.copy(notifications = emptyList()) }
        onMenuToggled()
    }

    fun onSettingsClick() {
        _navigationEvent.tryEmit(NotificationsNavigation.Settings)
    }

    fun onNotificationClicked(notification: NotificationItem) {
        _uiState.update { state ->
            val index = state.notifications.indexOf(notification)
            if (index >= 0) {
                val newNotifications = state.notifications.toMutableList()
                newNotifications[index] = notification.copy(isRead = true)
                state.copy(notifications = newNotifications)
            } else {
                state
            }
        }
    }

    fun onNotificationDismissed(notification: NotificationItem) {
        _uiState.update { state ->
            state.copy(notifications = state.notifications.filter { it.id != notification.id })
        }
    }
}