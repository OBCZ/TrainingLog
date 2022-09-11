package com.baarton.traininglog.app

import android.app.Application
import com.baarton.traininglog.db.DatabaseModule
import com.baarton.traininglog.db.IDatabaseModule
import com.baarton.traininglog.viewmodel.AddRecordViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                module {
                    viewModel { AddRecordViewModel() }

                    single<IAppInfo> { AppInfo }
                    single<IDatabaseModule> { DatabaseModule(get()) }
                }
            )
        }
    }

}