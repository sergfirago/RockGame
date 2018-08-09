package com.firago.serg.rockpaperapplication.di


import com.firago.serg.rockpaperapplication.logic.Communion
import com.firago.serg.rockpaperapplication.logic.Talker
import com.firago.serg.rockpaperapplication.ui.model.MainViewModel
import com.firago.serg.rockpaperapplication.voiceworker.gametext.CommunionImpl
import com.firago.serg.rockpaperapplication.voiceworker.speechkit.GameRecognizerListener
import com.firago.serg.rockpaperapplication.voiceworker.speechkit.TalkerImpl
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val module: Module = applicationContext {
    viewModel { MainViewModel(get(), get()) }
    bean { TalkerImpl(androidApplication(), get()) as Talker }
    bean { GameRecognizerListener(get()) }
    bean { CommunionImpl(androidApplication()) as Communion }

}