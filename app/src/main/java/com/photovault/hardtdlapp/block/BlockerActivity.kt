package com.photovault.hardtdlapp.block

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.photovault.hardtdlapp.data.BlockMode
import com.photovault.hardtdlapp.ui.theme.HardTDLappTheme
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class BlockerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modeName = intent.getStringExtra(EXTRA_MODE) ?: BlockMode.NOTIFICATION.name
        val endMillis = intent.getLongExtra(EXTRA_END_TIME, 0L)
        val endTime = Instant.ofEpochMilli(endMillis)
            .atZone(ZoneId.systemDefault()).toLocalDateTime()

        enableEdgeToEdge()
        setContent {
            HardTDLappTheme {
                BlockerScreen(mode = BlockMode.valueOf(modeName), endTime = endTime)
            }
        }
    }

    override fun onBackPressed() {
        val endMillis = intent.getLongExtra(EXTRA_END_TIME, 0L)
        if (System.currentTimeMillis() >= endMillis) {
            super.onBackPressed()
        }
        // otherwise ignore back press
    }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val EXTRA_END_TIME = "extra_end_time"
    }
}

@Composable
private fun BlockerScreen(mode: BlockMode, endTime: LocalDateTime) {
    val remaining = remember { mutableStateOf(Duration.between(LocalDateTime.now(), endTime)) }
    LaunchedEffect(endTime) {
        while (remaining.value > Duration.ZERO) {
            delay(1000)
            remaining.value = Duration.between(LocalDateTime.now(), endTime)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (remaining.value <= Duration.ZERO) {
            Text(text = "Task completed", style = MaterialTheme.typography.headlineMedium)
        } else {
            val minutes = remaining.value.toMinutes()
            val seconds = remaining.value.seconds % 60
            Text(text = "${mode.name} - ${minutes}m ${seconds}s left", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
