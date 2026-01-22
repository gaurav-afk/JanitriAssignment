package com.towerofapp.janitriassignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.towerofapp.janitriassignment.R
import com.towerofapp.janitriassignment.data.local.VitalEntity
import com.towerofapp.janitriassignment.ui.viewmodel.VitalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            TopAppBar(title = { Text("Track My Pregnancy") })
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
            modifier = modifier
                .padding(padding)
        ) {
            items(vitals.reversed()) { vital ->
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3C6FF)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier) {
                    VitalRow(
                        icon = painterResource(R.drawable.heart_rate),
                        text = "${vital.heartRate} bpm"
                    )
                    VitalRow(
                        icon = painterResource(R.drawable.weight_scale),
                        text = "${vital.weightKg} kg"
                    )

                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(Modifier) {
                    VitalRow(
                        icon = painterResource(R.drawable.bp_monitor),
                        text = "${vital.systolicPressure}/${vital.diastolicPressure} mmHg"
                    )
                    VitalRow(
                        icon = painterResource(R.drawable.baby_kicks),
                        text = "${vital.babyKicksCount} kicks"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF9C4DCC),
                        shape = RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatTimestamp(vital.timestamp),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat(
        "EEE, dd MMM yyyy hh:mm a",
        Locale.getDefault()
    )
    return formatter.format(Date(timestamp))
}

@Composable
fun VitalRow(
    icon: Painter,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = "Favorite",
            tint = Color.Black,
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
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