package com.yourcompany.focusapp.di

import android.content.Context
import androidx.room.Room
import com.yourcompany.focusapp.data.database.AppDatabase
import com.yourcompany.focusapp.data.repository.TaskRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "focus.db"
        ).build()

    @Provides
    fun provideTaskDao(db: AppDatabase) = db.taskDao()

    @Provides
    fun provideRepository(dao: com.yourcompany.focusapp.data.database.TaskDao) =
        TaskRepository(dao)
}
