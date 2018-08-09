package com.firago.serg.rockpaperapplication.voiceworker.speechkit;

import android.content.Context;

import com.firago.serg.rockpaperapplication.R;

import java.util.UUID;


import ru.yandex.speechkit.SpeechKit;

public class SpeechKitUtil {
    public static void init(Context context) throws SpeechKit.LibraryInitializationException {
        String apiKey = context.getString(R.string.yandex_speetchkit_api_key);
        SpeechKit.getInstance().init(context.getApplicationContext(), apiKey);
        SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
//        SpeechKit.getInstance().setLogLevel(SpeechKit.LogLevel.LOG_DEBUG);
    }
}
