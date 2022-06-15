package com.example.blockpuzzle.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Scoreboard {

    var points = 0

    private var x = 0
    private var y = 0
    private var w = 0
    private var h = 0

    private val textPaint = Paint().apply {
        Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 120f
        isAntiAlias = true
    }

        fun set(newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
            x = newX
            y = newY
            w = newWidth
            h = newHeight
        }

        fun draw(canvas: Canvas?) {
            canvas?.drawText(points.toString(), x.toFloat(), y.toFloat(), textPaint)
        }
}