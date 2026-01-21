package com.towerofapp.janitriassignment.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.towerofapp.janitriassignment.data.local.VitalEntity
import com.towerofapp.janitriassignment.ui.viewmodel.VitalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: VitalViewModel,
    modifier: Modifier
) {
    val vitals by viewModel.vitals.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Vitals") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding)
        ) {
            items(vitals) { vital ->
                VitalItem(vital)
            }
        }
    }

    if (showDialog) {
        AddVitalDialog(
            onDismiss = { showDialog = false },
            onSave = { vital ->
                viewModel.addVitals(vital)
                showDialog = false
            }
        )
    }
}

@Composable
fun VitalItem(vital: VitalEntity) {
    Text(
        text = "BP: ${vital.systolicPressure}/${vital.diastolicPressure} | HR: ${vital.heartRate}",
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun AddVitalDialog(
    onDismiss: () -> Unit,
    onSave: (VitalEntity) -> Unit
) {
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var kicks by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Vital") },
        text = {
            Column {
                TextField(
                    value = systolic,
                    onValueChange = { systolic = it },
                    label = { Text("Systolic Pressure") }
                )
                TextField(
                    value = diastolic,
                    onValueChange = { diastolic = it },
                    label = { Text("Diastolic Pressure") }
                )
                TextField(
                    value = heartRate,
                    onValueChange = { heartRate = it },
                    label = { Text("Heart Rate") }
                )
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") }
                )
                TextField(
                    value = kicks,
                    onValueChange = { kicks = it },
                    label = { Text("Baby Kicks Count") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        VitalEntity(
                            systolicPressure = systolic.toInt(),
                            diastolicPressure = diastolic.toInt(),
                            heartRate = heartRate.toInt(),
                            weightKg = weight.toFloat(),
                            babyKicksCount = kicks.toInt(),
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}