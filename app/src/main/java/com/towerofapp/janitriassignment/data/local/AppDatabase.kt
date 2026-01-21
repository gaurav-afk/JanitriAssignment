package com.towerofapp.janitriassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VitalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vitalDao(): VitalDao
}
