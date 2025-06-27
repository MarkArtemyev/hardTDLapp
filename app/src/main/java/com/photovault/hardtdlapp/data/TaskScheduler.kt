package com.photovault.hardtdlapp.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.photovault.hardtdlapp.block.BlockerActivity
import java.time.ZoneId

/**
 * Schedule tasks using AlarmManager. When a task triggers, BlockerActivity
 * is started to apply the selected BlockMode.
 */
class TaskScheduler(private val context: Context) {
    fun schedule(task: Task) {
        val intent = Intent(context, BlockerActivity::class.java).apply {
            putExtra(BlockerActivity.EXTRA_MODE, task.mode.name)
            val millis = task.scheduledTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            putExtra(BlockerActivity.EXTRA_END_TIME, millis)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pending = PendingIntent.getActivity(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerAtMillis = task.scheduledTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending)
        Log.d("TaskScheduler", "Scheduled task ${task.id} at ${task.scheduledTime} mode ${task.mode}")
    }
}
