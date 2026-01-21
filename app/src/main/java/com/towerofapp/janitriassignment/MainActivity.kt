package com.towerofapp.janitriassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
        setContent {
            JanitriAssignmentTheme {
                val dao = MyApp.database.vitalDao()
                val repository = remember { VitalRepository(dao) }

                val viewModel = remember {
                    VitalViewModel(repository)
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

