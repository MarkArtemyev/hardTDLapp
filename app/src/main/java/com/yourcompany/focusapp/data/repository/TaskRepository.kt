package com.yourcompany.focusapp.data.repository

import com.yourcompany.focusapp.data.database.TaskDao
import com.yourcompany.focusapp.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    fun getTasks(): Flow<List<Task>> = dao.getTasks()
    suspend fun addTask(task: Task) = dao.insert(task)
    suspend fun updateTask(task: Task) = dao.update(task)
    suspend fun deleteTask(task: Task) = dao.delete(task)
}
