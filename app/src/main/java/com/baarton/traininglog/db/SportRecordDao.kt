package com.baarton.traininglog.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baarton.traininglog.model.SportRecord
import kotlinx.coroutines.flow.Flow


@Dao
interface SportRecordDao {

    @Query("SELECT * FROM sport_records")
    fun getAll(): Flow<List<SportRecord>>

    //TODO how to deal with conflicts?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: SportRecord)

    @Query("DELETE FROM sport_records")
    suspend fun nuke()

}