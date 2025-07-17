package com.yourcompany.focusapp.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.yourcompany.focusapp.util.NotificationHelper

class FocusService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationHelper.createServiceNotification(this)
        startForeground(NotificationHelper.SERVICE_ID, notification)
        return START_NOT_STICKY
    }
}
