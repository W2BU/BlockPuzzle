package com.example.blockpuzzle.gameobjects

import android.content.Context
import android.graphics.*
import com.example.blockpuzzle.common.getBitmapFromAsset


class Board (
    context: Context,
    private val size: Int = 0
) {
    private val paint: Paint = Paint()
    private val blockBox: Rect = Rect()
    private val emptyBlockSprite: Bitmap? = getBitmapFromAsset(context, "grid_block.png")
    private val blockSprite: Bitmap? = getBitmapFromAsset(context, "block_tile.png")

    private var boundX = 0
    private var boundY = 0
    private var boundWidth = 0

    private val matrix: MutableList<MutableList<Boolean>> = MutableList(size) { MutableList(size) {false} }


    fun draw(canvas: Canvas?) {
        val cellWidth = (boundWidth / size);
        for (row in 0 until size) {
            for (col in 0 until size) {
                blockBox.left = col * cellWidth + boundX
                blockBox.top = row * cellWidth + boundY
                blockBox.right = blockBox.left + cellWidth
                blockBox.bottom = blockBox.top + cellWidth
                if (emptyBlockSprite != null && blockSprite != null) {
                    if (!matrix[row][col]) {
                        canvas?.drawBitmap(emptyBlockSprite, null, blockBox, paint)
                    } else {
                        canvas?.drawBitmap(blockSprite, null, blockBox, paint)
                    }
                }
            }
        }
    }

    fun placeGroupBlock(
        index: Pair<Int, Int>,
        groupBlockMatrix: List<List<Boolean>>
    ) {
        for (row in groupBlockMatrix.indices) {
            for (col in 0 until groupBlockMatrix[0].size) {
                val matrixRow: Int = index.first + row
                val matrixCol: Int = index.second + col
                if (groupBlockMatrix[row][col] &&
                    row in 0 until matrix.size &&
                    col in 0 until matrix[0].size
                ) {
                    matrix[matrixRow][matrixCol] = true
                }
            }
        }
    }

    fun getCellIndexByCoordinates(x: Float, y: Float): Pair<Int, Int>? {
        val blockWidth: Float = (boundWidth / 8).toFloat()
        for (row in 0 until matrix.size) {
            for (col in 0 until matrix[0].size) {
                val blockX1: Float = col * blockWidth + x
                val blockX2 = blockX1 + blockWidth
                val blockY1: Float = row * blockWidth + y
                val blockY2 = blockY1 + blockWidth
                if (x >= blockX1 &&
                    x < blockX2 &&
                    y >= blockY1 &&
                    y < blockY2
                ) {
                    return Pair(row, col)
                }
            }
        }
        return null
    }

    fun canInsert(
        index: Pair<Int, Int>,
        groupBlockMatrix: List<List<Boolean>>
    ): Boolean {
        for (row in groupBlockMatrix.indices) {
            for (col in 0 until groupBlockMatrix[0].size) {
                val matrixRow: Int = index.first + row
                val matrixCol: Int = index.second + col
                if (matrixRow >= matrix.size || matrixCol >= matrix[0].size) {
                    return false
                }
                if (!groupBlockMatrix[row][col] && matrix[matrixRow][matrixCol]) {
                    return false
                }
            }
        }
        return true
    }

    fun deleteBlocks() {
        for (row in matrix.indices) {
            if (rowIsFull(row)) {
                deleteRow(row)
            }
        }
        for (col in 0 until matrix[0].size) {
            if (colIsFull(col)) {
                deleteCol(col)
            }
        }
    }

    fun setBounds(x: Int, y: Int, width: Int) {
        boundX = x
        boundY = y
        boundWidth = width
    }

    private fun rowIsFull(row: Int): Boolean {
        for (col in 0 until matrix[0].size) {
            if (!matrix[row][col]) {
                return false
            }
        }
        return true
    }

    private fun colIsFull(col: Int): Boolean {
        for (row in 0 until matrix[0].size) {
            if (!matrix[row][col]) {
                return false
            }
        }
        return true
    }

    private fun deleteRow(row: Int) {
        for (col in 0 until matrix[0].size) {
            matrix[row][col] = false
        }
    }

    private fun deleteCol(col: Int) {
        for (row in matrix.indices) {
            matrix[row][col] = false
        }
    }

    fun checkBlockCanBePlaced(matrixBlockGroup: List<List<Boolean>>): Boolean {
        for (row in matrix.indices) {
            for (col in 0 until matrix[0].size) {
                if (canInsert(Pair(row, col), matrixBlockGroup)) {
                    return true
                }
            }
        }
        return false
    }

    fun resetBoard() {
        for (row in matrix.indices) {
            for (col in 0 until matrix[0].size) {
                matrix[row][col] = false
            }
        }
    }

}