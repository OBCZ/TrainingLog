package com.baarton.traininglog.repo

import com.baarton.traininglog.model.SportRecord
import kotlinx.coroutines.flow.Flow


interface IRepositoryModule {

    suspend fun addRecord(recordToStore: SportRecord)
    fun getRecords(): Flow<List<SportRecord>>
    suspend fun nuke()
}