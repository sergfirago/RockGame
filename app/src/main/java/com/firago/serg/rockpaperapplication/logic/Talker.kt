package com.firago.serg.rockpaperapplication.logic

import android.annotation.SuppressLint

interface Talker {
    var onResultListener: ((ResultWord) -> Unit)?
    @SuppressLint("MissingPermission")
    fun startRecord()
    fun say(phrase: String)
}