package com.example.indexcardtrainer.core.domain.util

import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

object DateTimeConverter {
    fun parseDateTime(timestamp : Long) : LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId())
    }

    fun formatRuntime(runtimeSeconds: Long) : String {
        val minutes = runtimeSeconds / 60
        return if (minutes > 0) {
            val hours = minutes / 60
            if (hours > 0) {
                "${(hours).toString().padStart(2, '0')}:${(minutes / 60).toString().padStart(2, '0')}:${(runtimeSeconds % 60).toString().padStart(2, '0')} h"
            } else {
                "${(minutes).toString().padStart(2, '0')}:${(runtimeSeconds % 60).toString().padStart(2, '0')} min"
            }
        } else {
            "$runtimeSeconds sec"
        }
    }
}