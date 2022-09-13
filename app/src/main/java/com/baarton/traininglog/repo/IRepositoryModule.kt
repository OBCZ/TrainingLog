package com.baarton.traininglog.repo

import com.baarton.traininglog.model.SportRecord


interface IRepositoryModule {

    suspend fun addRecord(recordToStore: SportRecord)
}