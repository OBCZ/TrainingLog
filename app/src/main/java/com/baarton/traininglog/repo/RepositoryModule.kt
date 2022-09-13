package com.baarton.traininglog.repo

import com.baarton.traininglog.db.IDatabaseModule
import com.baarton.traininglog.model.SportRecord
import org.koin.java.KoinJavaComponent


class RepositoryModule : IRepositoryModule {

    private val dbModule: IDatabaseModule by KoinJavaComponent.inject(IDatabaseModule::class.java)
    //TODO API service

    override suspend fun addRecord(recordToStore: SportRecord) {
        dbModule.db.sportRecordDao().insert(recordToStore)

    }

}