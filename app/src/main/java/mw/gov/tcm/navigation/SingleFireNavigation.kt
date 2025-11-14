package mw.gov.tcm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> mutableSingleFireNavigation() = MutableSharedFlow<T>(replay = 0, extraBufferCapacity = 1)

@Composable
fun <T> Flow<T>.ObserveSingleFireNavigation(owner: LifecycleOwner = LocalLifecycleOwner.current, onNavigate: (T) -> Unit) {
    LaunchedEffect(Unit) {
        owner.lifecycleScope.launch {
            this@ObserveSingleFireNavigation.flowWithLifecycle(owner.lifecycle, Lifecycle.State.STARTED).collect(onNavigate)
        }
    }
}