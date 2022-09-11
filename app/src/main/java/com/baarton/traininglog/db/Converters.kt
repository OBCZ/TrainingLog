package com.baarton.traininglog.db

import androidx.room.TypeConverter
import kotlin.time.Duration


class Converters {

    @TypeConverter
    fun fromDuration(duration: Duration): String {
        return duration.toIsoString()
    }

    @TypeConverter
    fun toDuration(durationStr: String): Duration {
        return Duration.parseIsoString(durationStr)
    }

}