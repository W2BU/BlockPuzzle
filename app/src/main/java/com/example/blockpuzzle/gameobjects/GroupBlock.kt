package com.example.blockpuzzle.gameobjects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

enum class GroupBlockState {
    INIT,
    HOME,
    IDLE,
}

class GroupBlock (
    private var middleX: Float,
    private var middleY: Float,
    private var childSizeNormal: Float,
    private var childSizeMaximum: Float,
    private val matrix: List<List<Boolean>>,
    private val context: Context?
){
    private var childSizeCurrent: Float = 0f
    private var blockToDraw = Block(0f, 0f, childSizeCurrent, childSizeCurrent, context)

    private var hidden = false
    private var pressed = false

    private var homePosX = 0
    private var homePosY = 0
    private var yMaxDistance: Float = 0f

    private var state = GroupBlockState.INIT

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
        blockToDraw.setSize(childSizeCurrent.toFloat())
        val groupBlockWidth: Float = (matrix[0].size * childSizeCurrent).toFloat()
        val groupBlockHeight: Float = (matrix.size * childSizeCurrent).toFloat()
        val left = middleX - groupBlockWidth / 2
        val top = middleY - groupBlockHeight / 2
        for (row in matrix.indices) {
            for (col in 0 until matrix[0].size) {
                if (matrix[row][col]) {
                    blockToDraw.setX(left + col * childSizeCurrent)
                    blockToDraw.setY(top + row * childSizeCurrent)
                    if (canvas != null) {
                        blockToDraw.draw(canvas, paint)
                    }
                }
            }
        }
    }

    fun update(deltaTime: Long) {
        if (hidden) {
            return
        }
        when (state) {
           GroupBlockState.INIT -> {
                if (childSizeCurrent > childSizeNormal) {
                    childSizeCurrent = childSizeNormal
                    state = GroupBlockState.IDLE
                }
            }
           GroupBlockState.HOME -> {
                if (middleX < homePosX && middleX > homePosX ||
                    middleY < homePosY && middleY > homePosY
                ) {
                    middleX = homePosX.toFloat()
                    middleY = homePosY.toFloat()
                    state = GroupBlockState.IDLE
                    childSizeCurrent = childSizeNormal
                }
            }
            else -> {}
        }
    }

    fun setSizeMaximum(newSize: Float) {
        childSizeMaximum = newSize
        yMaxDistance = childSizeMaximum * 3
    }

    fun isHidden(): Boolean = hidden

    fun isPressed(): Boolean = pressed

    fun setIsPressed(newState: Boolean) { pressed = newState }

    fun setHidden(newState: Boolean) { hidden = newState }

    fun getFirstBlockMiddleX(): Float {
        val blockWidth: Float = matrix[0].size * childSizeMaximum
        return middleX - blockWidth / 2 + childSizeMaximum / 2
    }

    fun getFirstBlockMiddleY(): Float {
        val blockHeight: Float = matrix.size * childSizeMaximum
        return middleY - yMaxDistance - blockHeight / 2 + childSizeMaximum / 2
    }

    fun setMiddleX(newValue: Float) { middleX = newValue }

    fun setMiddleY(newValue: Float) { middleY = newValue }

    fun getBlockMatrix(): List<List<Boolean>> = matrix

    fun clone(): GroupBlock {
        val groupBlock = GroupBlock(
            middleX,
            middleY,
            childSizeNormal,
            childSizeMaximum,
            matrix,
            context)
        groupBlock.hidden = hidden
        groupBlock.blockToDraw = blockToDraw
        groupBlock.homePosX = homePosX
        groupBlock.homePosY = homePosY
        groupBlock.childSizeCurrent = childSizeCurrent
        groupBlock.yMaxDistance = yMaxDistance
        return groupBlock
    }

    fun moveHome(homeX: Float, homeY: Float) {
        homePosX = homeX.toInt()
        homePosY = homeY.toInt()
        state = GroupBlockState.HOME
    }

    fun reset() {
        state = GroupBlockState.INIT
        setHidden(false)
    }

}