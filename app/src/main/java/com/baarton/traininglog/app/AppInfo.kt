package com.baarton.traininglog.app

import com.baarton.traininglog.BuildConfig


object AppInfo : IAppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
    override val debug: Boolean = BuildConfig.DEBUG
    override val version: String = BuildConfig.VERSION_NAME
}