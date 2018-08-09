package com.firago.serg.rockpaperapplication.ui.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import com.firago.serg.rockpaperapplication.R

class MicrophoneView : View {
    var paint: Paint = Paint()
    private var diameter = 50f
    private var currentColor: Int = Color.WHITE
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private val LOG_TAG: String = "View"

    var diameterBegin: Float = 30f

    var diameterEnd: Float = 55f

    var timeLoop: Int = 1000

    @ColorInt
    var colorBegin: Int = Color.WHITE
    @ColorInt
    var colorEnd: Int = Color.RED

    private var animatorValue = ValueAnimator.ofFloat(0f, 1f)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        val typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.MicrophoneView)
        diameterBegin = typedArray.getDimension(R.styleable.MicrophoneView_diameter_begin, 30f)
        diameterEnd = typedArray.getDimension(R.styleable.MicrophoneView_diameter_end, 50f)
        timeLoop = typedArray.getInteger(R.styleable.MicrophoneView_time, 1000)

        colorBegin = typedArray.getColor(R.styleable.MicrophoneView_color_begin, Color.WHITE)
        colorEnd = typedArray.getColor(R.styleable.MicrophoneView_color_end, Color.RED)
        currentColor = colorBegin

        typedArray.recycle()
        setupView()
        initAnimation()

    }

    private fun initAnimation() {
        animatorValue.duration = timeLoop.toLong()
        animatorValue.repeatCount = -1
        animatorValue.addUpdateListener {
            val value = it.animatedValue as Float
            diameter = diameterBegin + (diameterEnd - diameterBegin) * value
            val argbEvaluator = ArgbEvaluator()
            currentColor = argbEvaluator.evaluate(value, colorBegin, colorEnd) as Int
            invalidate()
        }
    }

    constructor(ctx: Context) : super(ctx) {
        setupView()
    }

    fun startFlip() {
        animatorValue.start()
    }

    fun stopFlip() {
        animatorValue.cancel()
    }

    private fun setupView() {
        paint.color = currentColor
        paint.style = Paint.Style.FILL
        diameter = diameterBegin
        invalidate()
    }



    override fun onDraw(canvas: Canvas) {
        paint.color = currentColor
        centerX = (width) / 2f
        centerY = (height) / 2f
        canvas.drawCircle(centerX, centerY, diameter/2, paint)
        super.onDraw(canvas)
    }

}