package com.kachi.redo

import android.app.Application
import androidx.room.Room
import com.kachi.redo.database.ToDoDatabase

class MainApplication: Application() {

    companion object {
        lateinit var instance: ToDoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.DATABASE_NAME
        ).build()
    }
}