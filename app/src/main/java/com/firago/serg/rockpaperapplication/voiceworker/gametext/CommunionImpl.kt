package com.firago.serg.rockpaperapplication.voiceworker.gametext

import android.content.Context
import com.firago.serg.rockpaperapplication.R
import com.firago.serg.rockpaperapplication.logic.AnswerState
import com.firago.serg.rockpaperapplication.logic.Communion
import com.firago.serg.rockpaperapplication.logic.intToState
import com.firago.serg.rockpaperapplication.logic.toIndex

operator fun List<String>.get(state: AnswerState): String{
    return this[state.toIndex()]
}
class CommunionImpl(val context: Context) : Communion {

    private val list = context.resources.getStringArray(R.array.list).toList()
//    listOf("камень", "ножницы", "бумага")
    private val list2 = context.resources.getStringArray(R.array.list2).toList()
//    listOf("камень", "ножницы", "бумагу")

    override fun getWord(state: AnswerState): String = list[state]

    override fun youLose(userState: AnswerState, appState: AnswerState): String{
        val userWord = list[userState]
        val appWord = list2[appState]
        return context.getString(R.string.you_lose_message, userWord, appWord)
//        return "Твой ответ $userWord. Я задумала $appWord. Я тебя победила! Представляешь?.. Давай опять играть!"
    }

    override fun youWin(userState: AnswerState, appState: AnswerState):String{
        val userWord = list[userState]
        val appWord = list[appState]
        return context.getString(R.string.you_win_message, userWord, appWord)
//        return "У тебя $userWord, у меня $appWord. Я проиграла и хочу отыграться! Начинай!"
    }

    override fun draw(state: AnswerState): String{
        val userWord = list[state]
        val appWord = list2[state]
        return context.getString(R.string.draw_message, userWord, appWord)
//        return "Твой ответ $userWord. И я задумала $appWord. У нас типа ничья. Давай еще!"
    }



    override fun getErrorPhrase() = context.getString(R.string.error_message)!!
//    "Какая-то ошибка. Сыграем еще?"




    override fun getStateAnswer(word: String): AnswerState = intToState(list.indexOf(word))

    //if any words from list contains in hypotheses,
    // then return last word (max index in hypotheses)
    //else ""
    override fun containsWord(hypothese: String): String {
        var index = -1
        var answer = ""
        for (word in list) {
            val indexOf = hypothese.indexOf(word)
            if (index < indexOf){
                index = indexOf
                answer = word
            }
        }
        return answer
    }
}