package com.baarton.traininglog.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sport_records")
data class SportRecord(

    @PrimaryKey(autoGenerate = true)
    val dbId: Int,

    @ColumnInfo(name = "sportName")
    val sportName: String,

    @ColumnInfo(name = "sportLocation")
    val sportLocation: String,

    @ColumnInfo(name = "sportDuration")
    val sportDuration: Duration
)