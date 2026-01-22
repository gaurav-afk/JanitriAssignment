package com.towerofapp.janitriassignment

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.towerofapp.janitriassignment.data.repository.VitalRepository
import com.towerofapp.janitriassignment.ui.screens.HomeScreen
import com.towerofapp.janitriassignment.ui.theme.JanitriAssignmentTheme
import com.towerofapp.janitriassignment.ui.viewmodel.VitalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        setContent {
            JanitriAssignmentTheme {
                val dao = MyApp.database.vitalDao()
                val repository = remember { VitalRepository(dao) }

                val viewModel = remember {
                    VitalViewModel(repository)
                }

                HomeScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
    }
}

