package com.firago.serg.rockpaperapplication.logic

sealed class ResultWord{
    class Begin: ResultWord()
    data class Success(val word: String): ResultWord()
    data class FailRecognizer(val error:String): ResultWord()
    class Ends: ResultWord()
    data class Text(val text: String): ResultWord()
    data class State(val state: String): ResultWord()
    data class FailSay(val error: String): ResultWord()
}