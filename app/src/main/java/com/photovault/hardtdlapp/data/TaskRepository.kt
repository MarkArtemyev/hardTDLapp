package com.photovault.hardtdlapp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Simple in-memory repository for tasks.
 */
class TaskRepository(private val scheduler: TaskScheduler) {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.value = (_tasks.value + task).sortedBy { it.scheduledTime }
        scheduler.schedule(task)
    }

    fun updateTask(updated: Task) {
        _tasks.value = _tasks.value.map { if (it.id == updated.id) updated else it }.sortedBy { it.scheduledTime }
        scheduler.schedule(updated)
    }

    fun deleteTask(id: Int) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }
}
