package com.photovault.hardtdlapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.photovault.hardtdlapp.ui.theme.HardTDLappTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val PREFS_NAME = "dragon_settings"
private const val KEY_NAME = "name"
private const val KEY_POWER = "power"
private const val KEY_SOUND = "sound"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HardTDLappTheme {
                DragonBreathSettingsScreen()
            }
        }
    }
}

private data class DragonSettings(
    val name: String,
    val power: Float,
    val soundEnabled: Boolean
)

private fun loadSettings(context: Context): DragonSettings {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return DragonSettings(
        name = prefs.getString(KEY_NAME, "Огненный ученик") ?: "Огненный ученик",
        power = prefs.getFloat(KEY_POWER, 50f),
        soundEnabled = prefs.getBoolean(KEY_SOUND, true)
    )
}

private fun saveSettings(context: Context, settings: DragonSettings) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit()
        .putString(KEY_NAME, settings.name)
        .putFloat(KEY_POWER, settings.power)
        .putBoolean(KEY_SOUND, settings.soundEnabled)
        .apply()
}

@Composable
private fun DragonBreathSettingsScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var power by remember { mutableFloatStateOf(50f) }
    var soundEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val loaded = loadSettings(context)
        name = loaded.name
        power = loaded.power
        soundEnabled = loaded.soundEnabled
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Дыхание дракона",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Настройте силу и характер дыхания. Параметры можно сохранить и восстановить после перезапуска.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Имя дракона") },
                        singleLine = true
                    )

                    Text(
                        text = "Сила пламени: ${power.roundToInt()}%",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Slider(
                        value = power,
                        onValueChange = { power = it },
                        valueRange = 0f..100f
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Звуковые эффекты")
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = { soundEnabled = it }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    saveSettings(
                        context,
                        DragonSettings(
                            name = name,
                            power = power,
                            soundEnabled = soundEnabled
                        )
                    )
                    scope.launch {
                        snackbarHostState.showSnackbar("Настройки сохранены")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить настройки")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DragonBreathSettingsPreview() {
    HardTDLappTheme {
        DragonBreathSettingsScreen()
    }
}
