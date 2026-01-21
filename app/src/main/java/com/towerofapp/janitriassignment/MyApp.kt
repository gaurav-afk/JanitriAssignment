package com.towerofapp.janitriassignment

import android.app.Application
import androidx.room.Room
import com.towerofapp.janitriassignment.data.local.AppDatabase

class MyApp : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
