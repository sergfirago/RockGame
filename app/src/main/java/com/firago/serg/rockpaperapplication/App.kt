package com.firago.serg.rockpaperapplication

import android.app.Application
import com.firago.serg.rockpaperapplication.di.module
import com.firago.serg.rockpaperapplication.voiceworker.speechkit.SpeechKitUtil
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SpeechKitUtil.init(this)
        startKoin(this, listOf(module))
    }
}