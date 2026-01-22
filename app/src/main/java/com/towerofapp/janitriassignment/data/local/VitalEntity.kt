package com.towerofapp.janitriassignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class VitalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val systolicPressure: Int,
    val diastolicPressure: Int,
    val heartRate: Int,
    val weightKg: Float,
    val babyKicksCount: Int,
    val timestamp: Long
)