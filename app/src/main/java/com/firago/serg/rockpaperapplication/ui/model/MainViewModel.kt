package com.firago.serg.rockpaperapplication.ui.model

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firago.serg.rockpaperapplication.logic.*
import java.util.*


sealed class Answer {
    data class Normal(val userState: AnswerState, val appState: AnswerState) : Answer()
    class Record : Answer()
    class Stop : Answer()
}

class MainViewModel(val talker: Talker, val communion: Communion) : ViewModel() {

    private var userWinCount = 0
    private var appWinCount = 0
    private val talkerListener: (ResultWord) -> Unit = {
        when (it) {
            is ResultWord.Text -> textLiveDate.value = it.text
            is ResultWord.Success -> {
                sayResult(it.word)
            }
            is ResultWord.FailRecognizer -> {
                word.value = ""
                sayError()
            }
            is ResultWord.Ends -> {
            }
            is ResultWord.FailSay -> {
                error.value = it.error
            }
        }
    }
    private val random = Random()


    val error = SingleLiveEvent<String>()
    val textLiveDate = MutableLiveData<String>()
    val word = MutableLiveData<String>()
    val answer = MutableLiveData<Answer>()


    init {
        talker.onResultListener = talkerListener
        answer.value = Answer.Stop()
    }

    @SuppressLint("MissingPermission")
    fun startRecording() {
        answer.value = Answer.Record()
        talker.startRecord()
    }


    private fun sayResult(word: String) {
        val answerUser = communion.getStateAnswer(word)
        val answerApp = intToState(random.nextInt(3))

        answer.value = Answer.Normal(answerUser, answerApp)
        when {
            answerApp == answerUser -> talker.say(communion.draw(answerApp))
            answerUser isWin answerApp -> {
                talker.say(communion.youWin(answerUser, answerApp))
                userWinCount++
            }
            else -> {
                talker.say(communion.youLose(answerUser, answerApp))
                appWinCount++
            }
        }

        this.word.value = "$word-${communion.getWord(answerApp)}. $userWinCount:$appWinCount"
    }

    private fun sayError() {
        talker.say(communion.getErrorPhrase())
        answer.value = Answer.Stop()
    }

}

private infix fun AnswerState.isWin(answerUser: AnswerState): Boolean {
    return com.firago.serg.rockpaperapplication.logic.isWin(this, answerUser)
}
