package com.photovault.hardtdlapp.data

/**
 * Modes of phone usage restriction.
 */
enum class BlockMode {
    FULL_FOCUS,  // Full lock except emergency calls
    FOCUSED,     // Block distracting apps
    NOTIFICATION // Normal mode with reminders
}
