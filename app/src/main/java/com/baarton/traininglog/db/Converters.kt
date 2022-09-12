package com.baarton.traininglog.db

import androidx.room.TypeConverter
import com.baarton.traininglog.model.Duration

class Converters {

    @TypeConverter
    fun fromDuration(duration: Duration): String {
        return duration.duration.toIsoString()
    }

    @TypeConverter
    fun toDuration(durationStr: String): Duration {
        return Duration(kotlin.time.Duration.parseIsoString(durationStr)) //TODO catch exception
    }

}