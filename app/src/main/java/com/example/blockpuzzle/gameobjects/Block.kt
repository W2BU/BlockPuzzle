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
) {

    private val blockBox = Rect(x.toInt(), y.toInt(), (x + w).toInt(), (y + h).toInt())
    private val blockSprite: Bitmap? = getBitmapFromAsset(context, "block_tile.png")

    fun draw(canvas: Canvas, paint: Paint) {
        paint.alpha = 255
        canvas.drawBitmap(blockSprite!!, null, blockBox, paint)
    }

    fun setSize(size: Float) {
        w = size
        h = size
    }

    fun setX(x: Float) {
        this.x = x
        blockBox.left = x.toInt()
        blockBox.right = (x + w).toInt()
    }
    fun setY(y: Float) {
        this.y = y
        blockBox.top = y.toInt()
        blockBox.bottom = (y + h).toInt()
    }
}