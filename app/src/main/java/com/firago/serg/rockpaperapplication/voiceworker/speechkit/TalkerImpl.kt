package com.firago.serg.rockpaperapplication.voiceworker.speechkit

import android.annotation.SuppressLint
import android.content.Context
import com.firago.serg.rockpaperapplication.R
import com.firago.serg.rockpaperapplication.logic.ResultWord
import com.firago.serg.rockpaperapplication.logic.Talker
import ru.yandex.speechkit.*


class TalkerImpl(context: Context, val listener: GameRecognizerListener) : Talker {


    override var onResultListener: ((ResultWord)->Unit)? = null
    set(value){
        listener.onResultListener = value
        gameVocalizer.onResultListener = value
    }

    private val gameVocalizer = GameVocalizer()

//    private val language: Language

    private var onlineRecognizer: OnlineRecognizer

    private var vocalizer: OnlineVocalizer

    init {

        val language = if (context.getString(R.string.language) == "ru-RU"){
            Language.RUSSIAN
        }else{
            Language.ENGLISH
        }

        vocalizer = OnlineVocalizer.Builder(language,
                gameVocalizer)
                .setEmotion(Emotion.GOOD)
                .setVoice(Voice.OKSANA)
                .build()

        onlineRecognizer = OnlineRecognizer.Builder(language, OnlineModel.NOTES, listener)
        .setDisableAntimat(false)
                .setEnablePunctuation(true)
                .build()

        vocalizer.prepare()
        onlineRecognizer.prepare()
    }

    @SuppressLint("MissingPermission")
    override fun startRecord() {
        vocalizer.cancel()
        onlineRecognizer.startRecording()
    }

    override fun say(phrase: String) {
        vocalizer.synthesize(phrase, Vocalizer.TextSynthesizingMode.APPEND)
    }
}