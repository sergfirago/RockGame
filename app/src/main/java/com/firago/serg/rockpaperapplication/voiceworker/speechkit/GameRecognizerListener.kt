package com.firago.serg.rockpaperapplication.voiceworker.speechkit

import com.firago.serg.rockpaperapplication.logic.Communion
import com.firago.serg.rockpaperapplication.logic.ResultWord
import ru.yandex.speechkit.*


class GameRecognizerListener(val communion: Communion): RecognizerListener {
    var  onResultListener:((ResultWord)->Unit)?=null
    override fun onRecordingDone(p0: Recognizer) {
        onResultListener?.invoke(ResultWord.State("onRecordingDone"))
    }

    override fun onPowerUpdated(p0: Recognizer, p1: Float) {
        onResultListener?.invoke(ResultWord.State("PowerUpdate"))
    }

    override fun onPartialResults(p0: Recognizer, result: Recognition, endOfUtterance: Boolean) {
        if (endOfUtterance){
            for(hypothese in result.hypotheses ){
                val word: String = communion.containsWord(hypothese.normalized.toLowerCase())
                if (word.isNotEmpty()){
                    onResultListener?.invoke(ResultWord.Success(word))
                    return
                }
            }
            onResultListener?.invoke(ResultWord.FailRecognizer("Empty"))

        }else{
            onResultListener?.invoke(ResultWord.Text(result.bestResultText))
        }

    }

    override fun onRecordingBegin(p0: Recognizer) {
        onResultListener?.invoke(ResultWord.Begin())
    }

    override fun onMusicResults(p0: Recognizer, p1: Track) {
        onResultListener?.invoke(ResultWord.State("MusicResult"))
    }

    override fun onSpeechEnds(p0: Recognizer) {
        onResultListener?.invoke(ResultWord.State("Speech Ends"))

    }

    override fun onRecognizerError(p0: Recognizer, p1: Error) {
        onResultListener?.invoke(ResultWord.FailRecognizer(p1.message))
        onResultListener?.invoke(ResultWord.Ends())
    }

    override fun onSpeechDetected(p0: Recognizer) {
        onResultListener?.invoke(ResultWord.State("Speech Detected"))
    }

    override fun onRecognitionDone(p0: Recognizer) {
        onResultListener?.invoke(ResultWord.Ends())
    }
}
