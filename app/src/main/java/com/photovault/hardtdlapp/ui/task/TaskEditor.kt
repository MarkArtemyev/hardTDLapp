package com.photovault.hardtdlapp.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.photovault.hardtdlapp.data.BlockMode
import com.photovault.hardtdlapp.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditor(
    initialTask: Task? = null,
    onConfirm: (Task) -> Unit,
    onDismiss: () -> Unit
) {
    val isEditing = initialTask != null
    val id = initialTask?.id ?: 0
    val titleState = remember { mutableStateOf(initialTask?.title ?: "") }
    val descState = remember { mutableStateOf(initialTask?.description ?: "") }
    val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val timeState = remember {
        mutableStateOf(initialTask?.scheduledTime?.format(timeFormatter) ?: "")
    }
    val expanded = remember { mutableStateOf(false) }
    val modeState = remember { mutableStateOf(initialTask?.mode ?: BlockMode.NOTIFICATION) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val time = try {
                    LocalDateTime.parse(timeState.value, timeFormatter)
                } catch (e: Exception) {
                    LocalDateTime.now()
                }
                onConfirm(
                    Task(
                        id = id,
                        title = titleState.value,
                        description = descState.value,
                        scheduledTime = time,
                        mode = modeState.value
                    )
                )
            }) {
                Text(if (isEditing) "Update" else "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text(if (isEditing) "Edit Task" else "New Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descState.value,
                    onValueChange = { descState.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = timeState.value,
                    onValueChange = { timeState.value = it },
                    label = { Text("Time (yyyy-MM-dd HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = !expanded.value }
                ) {
                    TextField(
                        readOnly = true,
                        value = modeState.value.name,
                        onValueChange = {},
                        label = { Text("Mode") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        BlockMode.values().forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(mode.name) },
                                onClick = {
                                    modeState.value = mode
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

