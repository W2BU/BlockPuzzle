package com.example.blockpuzzle.gameobjects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GroupBlock (
    private var middleX: Float,
    private var middleY: Float,
    private var childSizeNormal: Float,
    private val matrix: List<List<Boolean>>,
    private val context: Context?
){
    private var blockToDraw = Block(0f, 0f, childSizeNormal, childSizeNormal, context)

    private var hidden = false
    private var pressed = false

    private var homePosX = 0
    private var homePosY = 0
    private var yMaxDistance: Float = 0f


    fun isInArea(x: Float, y: Float): Boolean {
        return !hidden &&
                x > middleX - childSizeNormal * 2 &&
                x < middleX + childSizeNormal * 2 &&
                y > middleY - childSizeNormal * 2 &&
                y < middleY + childSizeNormal * 2
    }

    fun setChildSizeNormal(newSize: Float) {
        childSizeNormal = newSize
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        if (hidden) {
            return
        }
        blockToDraw.setSize(childSizeNormal)
        val groupBlockWidth: Float = (matrix[0].size * childSizeNormal)
        val groupBlockHeight: Float = (matrix.size * childSizeNormal)
        val left = middleX - groupBlockWidth / 2
        val top = middleY - groupBlockHeight / 2

        for (row in matrix.indices) {
            for (col in 0 until matrix[0].size) {
                if (matrix[row][col]) {
                    blockToDraw.setX(left + col * childSizeNormal)
                    blockToDraw.setY(top + row * childSizeNormal)
                    if (canvas != null) {
                        blockToDraw.draw(canvas, paint)
                    }
                }
            }
        }
        paint.color = Color.RED
        canvas?.drawCircle(getBlockMiddleX(), getBlockMiddleY(), 5f, paint)
    }

    fun isHidden(): Boolean = hidden

    fun isPressed(): Boolean = pressed

    fun setIsPressed(newState: Boolean) { pressed = newState }

    fun setHidden(newState: Boolean) { hidden = newState }

    fun getBlockMiddleX(): Float {
        val blockWidth: Float = matrix[0].size * childSizeNormal
        return middleX - blockWidth / 2 + childSizeNormal / 2
    }

    fun getBlockMiddleY(): Float {
        val blockHeight: Float = matrix.size * childSizeNormal
        return middleY - yMaxDistance - blockHeight / 2 + childSizeNormal / 2
    }

    fun setMiddleX(newValue: Float) { middleX = newValue }

    fun setMiddleY(newValue: Float) { middleY = newValue }

    fun getBlockMatrix(): List<List<Boolean>> = matrix

    fun clone(): GroupBlock {
        val groupBlock = GroupBlock(
            middleX,
            middleY,
            childSizeNormal,
            matrix,
            context)
        groupBlock.hidden = hidden
        groupBlock.blockToDraw = blockToDraw
        groupBlock.homePosX = homePosX
        groupBlock.homePosY = homePosY
        groupBlock.yMaxDistance = yMaxDistance
        return groupBlock
    }

    fun moveHome(homeX: Float, homeY: Float) {
        middleX = homeX
        middleY = homeY
    }

    fun reset() {
        hidden = false
    }


}