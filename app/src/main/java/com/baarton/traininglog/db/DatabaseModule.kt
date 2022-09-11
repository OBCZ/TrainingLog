package com.baarton.traininglog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baarton.traininglog.model.SportRecord


class DatabaseModule(context: Context) : IDatabaseModule {

    override val db: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "sport_records_db").build()

    @Database(entities = [SportRecord::class], version = 1)
    @TypeConverters(Converters::class)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun sportRecordDao(): SportRecordDao

    }

}