package com.firago.serg.rockpaperapplication.voiceworker.speechkit

import com.firago.serg.rockpaperapplication.logic.ResultWord
import ru.yandex.speechkit.Error
import ru.yandex.speechkit.Synthesis
import ru.yandex.speechkit.Vocalizer
import ru.yandex.speechkit.VocalizerListener

class GameVocalizer: VocalizerListener {
    var  onResultListener: ((ResultWord)->Unit)?=null

    override fun onPlayingBegin(p0: Vocalizer) {

    }

    override fun onVocalizerError(p0: Vocalizer, p1: Error) {
        onResultListener?.invoke(ResultWord.FailSay(p1.message))
    }

    override fun onSynthesisDone(p0: Vocalizer) {
    }

    override fun onPartialSynthesis(p0: Vocalizer, p1: Synthesis) {
    }

    override fun onPlayingDone(p0: Vocalizer) {
    }
}