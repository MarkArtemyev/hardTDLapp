package com.photovault.hardtdlapp.data

import java.time.LocalDateTime

/**
 * Data class representing a productivity task.
 */
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val scheduledTime: LocalDateTime,
    val mode: BlockMode
)
