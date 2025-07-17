package com.yourcompany.focusapp.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yourcompany.focusapp.R

object NotificationHelper {
    const val SERVICE_ID = 1

    fun createServiceNotification(context: Context): Notification {
        createChannel(context)
        return NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("Focus session running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun createChannel(context: Context) {
        val channel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            "Focus Notifications",
            NotificationManager.IMPORTANCE_LOW
        )
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}
