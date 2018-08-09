package com.firago.serg.rockpaperapplication.logic

enum class AnswerState{
    ROCK, SCISSORS, PAPER

}
fun intToState(answer: Int): AnswerState {
    return when (answer) {
        0 -> AnswerState.ROCK
        1 -> AnswerState.SCISSORS
        2 -> AnswerState.PAPER
        else -> throw IndexOutOfBoundsException("answer = $answer")
    }
}
fun stateToIndex(state: AnswerState): Int{
    return when(state){
        AnswerState.ROCK -> 0
        AnswerState.SCISSORS -> 1
        AnswerState.PAPER -> 2
    }
}
fun AnswerState.toIndex()= stateToIndex(this)


fun isWin(player1: AnswerState, player2: AnswerState): Boolean{
    return player1 == AnswerState.ROCK && player2 == AnswerState.SCISSORS ||
            player1 == AnswerState.SCISSORS && player2 == AnswerState.PAPER ||
            player1 == AnswerState.PAPER && player2 == AnswerState.ROCK
}
