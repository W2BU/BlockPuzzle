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
import com.example.blockpuzzle.gameobjects.Scoreboard

enum class State {
    PLAYING,
    GAME_OVER
}

class GameWorld(
    context: Context
): View.OnTouchListener {
    private val board = Board(context, 8)
    private val blockPool: MutableList<GroupBlock> = mutableListOf()
    private val blockDealer = BlockManager(context)
    private val scoreboard = Scoreboard()

    private var deviceHeight = 0
    private var deviceWidth = 0

    private var gameState = State.PLAYING
    private var isFirstSet = true

    private var hookedBlocksY: Float = 0f
    private var hookedBlocksX: MutableList<Float> = mutableListOf()

    //Background
    private val paint = Paint()

    private fun generatePool() {
        blockPool.clear()
        for (i in 0 until 3) {
            blockPool.add(blockDealer.getRandomBlock())
        }
    }

    init {
        generatePool()
    }

    fun render(canvas: Canvas?) {
        paint.color = Color.WHITE
        // Background
        canvas?.drawRect(0f, 0f, deviceWidth.toFloat(), deviceHeight.toFloat(), paint)

        scoreboard.draw(canvas)
        board.draw(canvas, paint)

        for (block in blockPool) {
            block.draw(canvas, paint)
        }
    }


    fun set(newDeviceWidth: Int, newDeviceHeight: Int) {
        if (!isFirstSet) {
            return
        }
        isFirstSet = false

        deviceWidth = newDeviceWidth
        deviceHeight = newDeviceHeight

        val boardY: Float = deviceHeight * 0.15f
        val boardX: Float = deviceWidth * 0.05f
        val boardWidth: Float = deviceWidth * 0.9f
        board.setBounds(boardX.toInt(), boardY.toInt(), boardWidth.toInt())

        scoreboard.set(
            (boardX + (boardWidth / 2)).toInt(),
            (deviceHeight * 0.1f).toInt(),
            (deviceWidth * 0.1f).toInt(),
            (deviceHeight * 0.01f).toInt()
        )

        blockDealer.setBlockWidth(boardWidth / 8)

        hookedBlocksY = boardY + boardWidth + deviceWidth * 0.25f
        val distanceX = boardWidth / 6
        hookedBlocksX.add(boardX + distanceX)
        hookedBlocksX.add(boardX + distanceX * 3)
        hookedBlocksX.add(boardX + distanceX * 5)
        setPoolBlocksPosition()

        for (block in blockPool) {
            block.setChildSizeNormal(boardWidth / 8)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (gameState) {
            State.PLAYING -> {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        processPress(event)
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

    private fun processPress(event: MotionEvent) {
        for (block in blockPool) {
            if (!block.isHidden() &&
                block.isInArea(event.x, event.y)
            ) {
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
                        block.getBlockMiddleX(),
                        block.getBlockMiddleY()
                    )
                    if (indexPair != null &&
                        board.canInsert(indexPair, block.getBlockMatrix())
                    ) {
                        board.placeGroupBlock(indexPair, block.getBlockMatrix())
                        block.setHidden(true)
                        scoreboard.points += board.scoreAndDeleteBlocks()
                    } else {
                        block.moveHome(
                            hookedBlocksX[i],
                            hookedBlocksY
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
            setPoolBlocksPosition()
        } else {
            if (checkGameOver()) {
                board.resetBoard()
                generatePool()
                setPoolBlocksPosition()
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

    private fun setPoolBlocksPosition() {
        for ((i, block) in blockPool.withIndex()) {
            block.setMiddleX(hookedBlocksX[i])
            block.setMiddleY(hookedBlocksY)
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