package com.yourcompany.focusapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourcompany.focusapp.data.model.Task
import com.yourcompany.focusapp.data.database.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
