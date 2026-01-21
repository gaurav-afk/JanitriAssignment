package com.towerofapp.janitriassignment.data.repository

import com.towerofapp.janitriassignment.data.local.VitalDao
import com.towerofapp.janitriassignment.data.local.VitalEntity
import kotlinx.coroutines.flow.Flow

class VitalRepository(private val vitalDao: VitalDao) {
    fun getVitals(): Flow<List<VitalEntity>> = vitalDao.getAllVitals()

    suspend fun addVitals(vital: VitalEntity) = vitalDao.insertVital(vital = vital)
}