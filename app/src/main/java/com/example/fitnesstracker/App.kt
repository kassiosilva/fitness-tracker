package com.example.fitnesstracker

import android.app.Application
import com.example.fitnesstracker.model.AppDatabase

class App : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
    }
}