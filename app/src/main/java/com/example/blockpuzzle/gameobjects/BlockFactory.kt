package com.example.blockpuzzle.gameobjects

import android.content.Context
import android.graphics.Canvas

class BlockFactory(private val context: Context?) {
    fun getOne(): GroupBlock {
        val blockMatrix = listOf(listOf(true))
        return GroupBlock(
            0f,
            0f,
            50f,
            60f,
            blockMatrix,
            context
        )
    }

    fun get2TowerH(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true),
            listOf(true)
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            60f,
            blockMatrix,
            context)
    }

    fun get2TowerV(): GroupBlock {
        val blockMatrix = listOf(listOf(true, true))
        return GroupBlock(
            0f,
            0f,
            50f,
            60f,
            blockMatrix,
            context)
    }

    fun get3TowerH(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true),
            listOf(true),
            listOf(true)
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            60f,
            blockMatrix,
            context)
    }

    fun get3TowerV(): GroupBlock {
        val blockMatrix = listOf(listOf(true, true, true))
        return GroupBlock(
            0f,
            0f,
            50f,
            60f,
            blockMatrix,
            context)
    }
}