package com.example.blockpuzzle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.example.blockpuzzle.gameobjects.BlockManager
import com.example.blockpuzzle.gameobjects.Board
import com.example.blockpuzzle.gameobjects.GroupBlock

enum class State {
    PLAYING,
    GAME_OVER
}

class GameWorld(
    context: Context
): View.OnTouchListener {
    private val board = Board(context, 8)

    private var deviceHeight = 0
    private var deviceWidth = 0

    private val blockPool: MutableList<GroupBlock> = mutableListOf()
    private val blockDealer = BlockManager(context)

    private var gameState = State.PLAYING
    private var isFirstSet = true

    private var hookedPosY: Float = 0f
    private var hookedPositionsX: MutableList<Float> = mutableListOf()
    
    //Background
    private val paint = Paint().apply {
        color = Color.WHITE
    }

    private fun generatePool() {
        for (i in 0 until 3) {
            blockPool.add(blockDealer.getRandomBlock())
        }
    }

    init {
        generatePool()
    }

    fun render(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, deviceWidth.toFloat(), deviceHeight.toFloat(), paint)
        board.draw(canvas)

        for (block in blockPool) {
            block.draw(canvas, paint)
        }
    }

    fun update(deltaTime: Long) {
        for (block in blockPool) {
            if (!block.isHidden()) {
                block.update(deltaTime)
            }
        }
        board.update(deltaTime)
    }

    fun set(deviceWidth: Int, deviceHeight: Int) {
        if (!isFirstSet) {
            return
        }
        isFirstSet = false

        this.deviceWidth = deviceWidth
        this.deviceHeight = deviceHeight
        val boardY: Float = deviceHeight * 0.1f
        val boardX: Float = deviceWidth * 0.1f
        val boardWidth: Float = deviceWidth * 0.8f
        board.setBounds(boardY.toInt(), boardX.toInt(), boardWidth.toInt())
        blockDealer.setMaxWidth(boardWidth / 8)


        hookedPosY = boardY + boardWidth + deviceWidth * 0.25f
        val distanceX = boardWidth / 6
        hookedPositionsX.add(boardX + distanceX)
        hookedPositionsX.add(boardX + distanceX * 3)
        hookedPositionsX.add(boardX + distanceX * 5)
        setGroupBlocksPosition()

        blockPool[0].setSizeMaximum(boardWidth / 8)
        blockPool[1].setSizeMaximum(boardWidth / 8)
        blockPool[2].setSizeMaximum(boardWidth / 8)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (gameState) {
            State.PLAYING -> {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        processPressing(event)
                    }
                    MotionEvent.ACTION_UP -> {
                        processTouch(event)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        processMoving(event)
                    }
                }
            }
            State.GAME_OVER -> {}
        }
        return true
    }

    private fun processPressing(event: MotionEvent) {
        for (block in blockPool) {
            if (!block.isHidden() &&
                block.isInArea(event.x, event.y)
            ) {
                //block.zoomOut()
                block.setIsPressed(true)
            }
        }
    }

    private fun processTouch(event: MotionEvent) {
        for ((i, block) in blockPool.withIndex()) {
            if (!block.isHidden()) {
                if (block.isPressed()) {
                    block.setIsPressed(false)
                    val indexPair = board.getCellIndexByCoordinates(
                        block.getFirstBlockMiddleX(),
                        block.getFirstBlockMiddleY()
                    )
                    if (indexPair != null &&
                        board.canInsert(indexPair, block.getBlockMatrix())
                    ) {
                        board.placeGroupBlock(indexPair, block.getBlockMatrix())
                        block.setHidden(true)
                        board.deleteBlocks()
                    } else {
                        block.moveHome(
                            hookedPositionsX[i],
                            hookedPosY
                        )
                    }
                }
            }
        }

        if (blockPool[0].isHidden() &&
            blockPool[1].isHidden() &&
            blockPool[2].isHidden()
        ) {
            generatePool()
            setGroupBlocksPosition()
        } else {
            if (checkGameOver()) {
                board.resetBoard()
            }
        }
    }

    private fun processMoving(event: MotionEvent) {
        for (block in blockPool) {
            if (!block.isHidden() && block.isPressed()) {
                block.setMiddleX(event.x)
                block.setMiddleY(event.y)
            }
        }
    }

    private fun setGroupBlocksPosition() {
        for ((i, block) in blockPool.withIndex()) {
            block.setMiddleX(hookedPositionsX[i])
            block.setMiddleY(hookedPosY)
            block.reset()
        }
    }

    private fun checkGameOver(): Boolean {
        var gameOver = true
        for (block in blockPool) {
            if (!block.isHidden() && board.checkBlockCanBePlaced(block.getBlockMatrix())) {
                gameOver = false
            }
        }
        return gameOver
    }


}