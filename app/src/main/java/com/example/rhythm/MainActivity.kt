package com.example.rhythm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rhythm.ui.theme.RhythmTheme

class MainActivity : ComponentActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val preferences = getSharedPreferences("rhythm", MODE_PRIVATE)
                if (!preferences.getBoolean("seeded", false)) {
                    preferences.edit().putBoolean("seeded", true).putBoolean("water", true)
                        .putBoolean("pushups", true).apply()
                }
                return RhythmViewModel(RhythmRepository(preferences)) as T
            }
        })[RhythmViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContent {
            RhythmTheme {
                RhythmApp(viewModel)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "rhythm", "Rhythm reminders", NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Personal hourly reminders" }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }
}
