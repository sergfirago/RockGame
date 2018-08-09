package com.firago.serg.rockpaperapplication.ui.view

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.firago.serg.rockpaperapplication.R
import com.firago.serg.rockpaperapplication.logic.AnswerState
import com.firago.serg.rockpaperapplication.ui.model.Answer
import com.firago.serg.rockpaperapplication.ui.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel


class MainActivity : AppCompatActivity() {
    val model: MainViewModel by viewModel()
    var text: String = ""
    val LOG_TAG = "MainActivity"
    private var okPermission: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestAudioPermission()

        observeModel()

        btStart.setOnClickListener {
            if (okPermission) {
                Log.d(LOG_TAG, "RECORD")
                model.startRecording()
            }else{
                requestAudioPermission()
            }
        }


    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1000) {
            if (permissions.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "$permissions")
                okPermission = true
            } else {
                okPermission = false
                ErrorDialog
                        .getInstance("Error", "Для работы нужен доступ RECORD_AUDIO" )
                        .show(supportFragmentManager, "ERROR_DIALOG")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun observeModel() {
        model.word.observe(this, Observer { word -> textView.text = word })
        model.error.observe(this, Observer {
            ErrorDialog.getInstance("Error", it!!).show(supportFragmentManager, "ERROR_DIALOD")
        })
        model.textLiveDate.observe(this, Observer {
            tvLog.text = it
            Log.d(LOG_TAG, "$it")
        })
        model.answer.observe(this, Observer { answer ->
            Log.d(LOG_TAG, "$answer")
            when (answer) {
                is Answer.Normal -> {
                    stopMicrophoneAnimation()
                    ivAppImageAnimation()
                    ivAppAnswer.visibility = View.VISIBLE
                    setImages(answer)
                }
                is Answer.Record -> {
                    startMicrophoneAnimator()
                    ivAppAnswer.visibility = View.INVISIBLE
                }
                is Answer.Stop -> {
                    ivAppAnswer.visibility = View.INVISIBLE
                    stopMicrophoneAnimation()
                }
            }
        })
    }

    private fun ivAppImageAnimation() {
        val scaleXAnimation = ObjectAnimator.ofFloat(ivAppAnswer, "scaleX", 0f, 1f)
        val scaleYAnimation = ObjectAnimator.ofFloat(ivAppAnswer, "scaleY", 0f, 1f)
        scaleXAnimation.duration = 400
        scaleYAnimation.duration = 400
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleXAnimation, scaleYAnimation)
        animatorSet.start()
    }

    private fun setImages(answer: Answer.Normal) {
        when (answer.userState) {
            AnswerState.ROCK -> ivUserChoose.setImageResource(R.drawable.rock)
            AnswerState.SCISSORS -> ivUserChoose.setImageResource(R.drawable.scissors)
            AnswerState.PAPER -> ivUserChoose.setImageResource(R.drawable.paper)
        }
        when (answer.appState) {
            AnswerState.ROCK -> ivAppAnswer.setImageResource(R.drawable.rock_hand)
            AnswerState.SCISSORS -> ivAppAnswer.setImageResource(R.drawable.sciss_hand)
            AnswerState.PAPER -> ivAppAnswer.setImageResource(R.drawable.paper_hand)
        }
    }

    private fun stopMicrophoneAnimation() {
        viewMicrophone.visibility = View.INVISIBLE
        viewMicrophone.stopFlip()
        btStart.isEnabled = true
    }


    private fun startMicrophoneAnimator() {
        ivUserChoose.setImageResource(R.drawable.microphone)
        btStart.isEnabled = false
        viewMicrophone.visibility = View.VISIBLE
        viewMicrophone.startFlip()
    }




    private fun requestAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat
                        .requestPermissions(this,
                                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                                1000)
                okPermission = false
                return
            }
        }
        okPermission = true
    }

}
