package com.towerofapp.janitriassignment

import android.app.Application
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.towerofapp.janitriassignment.data.local.AppDatabase
import com.towerofapp.janitriassignment.worker.VitalReminderWorker
import java.util.concurrent.TimeUnit


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

        scheduleVitalReminder()
    }

    private fun scheduleVitalReminder() {
        val workRequest =
            PeriodicWorkRequestBuilder<VitalReminderWorker>(
                5, TimeUnit.HOURS
            )
                .setInitialDelay(15, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "vital_reminder",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}