package com.photovault.hardtdlapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.photovault.hardtdlapp.ui.theme.HardTDLappTheme
import com.photovault.hardtdlapp.data.TaskRepository
import com.photovault.hardtdlapp.data.Task
import com.photovault.hardtdlapp.data.BlockMode
import com.photovault.hardtdlapp.data.TaskScheduler
import com.photovault.hardtdlapp.ui.task.TaskEditor
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = TaskRepository(TaskScheduler(applicationContext))
        setContent {
            HardTDLappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskScreen(
                        repository = repository,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TaskScreen(repository: TaskRepository, modifier: Modifier = Modifier) {
    val tasks by repository.tasks.collectAsState(initial = emptyList())
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var showEditor by remember { mutableStateOf(false) }

    if (showEditor) {
        TaskEditor(
            initialTask = editingTask,
            onConfirm = { task ->
                if (task.id == 0) {
                    val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    repository.addTask(task.copy(id = newId))
                } else {
                    repository.updateTask(task)
                }
                showEditor = false
                editingTask = null
            },
            onDismiss = {
                showEditor = false
                editingTask = null
            }
        )
    }

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.weight(1f, fill = true)) {
            items(tasks) { task ->
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = "${'$'}{task.title} - ${'$'}{task.mode}", modifier = Modifier.weight(1f))
                    IconButton(onClick = { editingTask = task; showEditor = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { repository.deleteTask(task.id) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
        FloatingActionButton(onClick = { showEditor = true }) {
            Text("+")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    HardTDLappTheme {
        val context = LocalContext.current
        TaskScreen(repository = TaskRepository(TaskScheduler(context)))
    }
}
