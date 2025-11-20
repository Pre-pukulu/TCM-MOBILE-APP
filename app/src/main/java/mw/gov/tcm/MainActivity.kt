package mw.gov.tcm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import mw.gov.tcm.navigation.NavGraph
import mw.gov.tcm.ui.theme.TCMAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TCMAppTheme {
                NavGraph()
            }
        }
    }
}