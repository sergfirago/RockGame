package com.firago.serg.rockpaperapplication.logic

interface Communion {
    fun youLose(userState: AnswerState, appState: AnswerState): String
    fun youWin(userState: AnswerState, appState: AnswerState): String
    fun draw(state: AnswerState): String
    fun getErrorPhrase(): String
    fun getStateAnswer(word: String): AnswerState
    fun getWord(state: AnswerState): String
    //if any words from list contains in hypotheses,
    // then return last word (max index in hypotheses)
    //else ""
    fun containsWord(hypothese: String): String
}