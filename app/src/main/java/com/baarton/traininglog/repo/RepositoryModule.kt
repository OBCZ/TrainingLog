package com.baarton.traininglog.repo

import com.baarton.traininglog.db.IDatabaseModule
import com.baarton.traininglog.model.SportRecord
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent


class RepositoryModule : IRepositoryModule {

    private val dbModule: IDatabaseModule by KoinJavaComponent.inject(IDatabaseModule::class.java)
    //TODO API service
    //TODO https://firebase.google.com/docs/database/rest/start

    override suspend fun addRecord(recordToStore: SportRecord) {
        dbModule.db.sportRecordDao().insert(recordToStore)
    }

    override fun getRecords(): Flow<List<SportRecord>> {
        return dbModule.db.sportRecordDao().getAll()
    }

    override suspend fun nuke() {
        return dbModule.db.sportRecordDao().nuke()
    }

}