package com.towerofapp.janitriassignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalDao {
    @Insert
    suspend fun insertVital(vital: VitalEntity)

    @Query("SELECT * FROM vitals")
    fun getAllVitals(): Flow<List<VitalEntity>>
}