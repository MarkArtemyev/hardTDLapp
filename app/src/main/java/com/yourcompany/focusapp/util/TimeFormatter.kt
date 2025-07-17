package com.yourcompany.focusapp.util

import java.util.concurrent.TimeUnit

object TimeFormatter {
    fun format(seconds: Long): String {
        val minutes = TimeUnit.SECONDS.toMinutes(seconds)
        val remaining = seconds - TimeUnit.MINUTES.toSeconds(minutes)
        return "%02d:%02d".format(minutes, remaining)
    }
}
