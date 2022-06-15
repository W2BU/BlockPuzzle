package com.example.blockpuzzle.gameobjects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.example.blockpuzzle.common.getBitmapFromAsset

class Block(
    private var x: Float,
    private var y: Float,
    private var w: Float,
    private var h: Float,
    context: Context?,
    private val blockSprite: Bitmap? = getBitmapFromAsset(context, "block_tile.png")
) {

    private val blockBox = Rect(x.toInt(), y.toInt(), (x + w).toInt(), (y + h).toInt())

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(blockSprite!!, null, blockBox, paint)
    }

    fun setSize(size: Float) {
        w = size
        h = size
    }

    fun setX(newX: Float) {
        x = newX
        blockBox.left = x.toInt()
        blockBox.right = (x + w).toInt()
    }

    fun setY(newY: Float) {
        y = newY
        blockBox.top = y.toInt()
        blockBox.bottom = (y + h).toInt()
    }
}