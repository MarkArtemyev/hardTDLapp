package com.yourcompany.focusapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import com.yourcompany.focusapp.util.Constants

@Entity(tableName = Constants.TASK_TABLE)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val scheduledTime: LocalDateTime,
    val mode: BlockMode = BlockMode.NOTIFICATION
)

enum class BlockMode {
    FULL_FOCUS,
    FOCUSED,
    NOTIFICATION
}
