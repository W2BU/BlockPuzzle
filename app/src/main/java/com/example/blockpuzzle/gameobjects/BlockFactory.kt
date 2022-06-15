package com.example.blockpuzzle.gameobjects

import android.content.Context

class BlockFactory(private val context: Context?) {
    fun getCubeOne(): GroupBlock {
        val blockMatrix = listOf(listOf(true))
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context
        )
    }

    fun getCubeTwo(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true, true),
            listOf(true, true),
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }

    fun getCubeThree(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true, true, true),
            listOf(true, true, true),
            listOf(true, true, true),
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }

    fun get2TowerV(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true),
            listOf(true)
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }

    fun get2TowerH(): GroupBlock {
        val blockMatrix = listOf(listOf(true, true))
        return GroupBlock(
            0f,
            0f,
            50f,
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
            blockMatrix,
            context)
    }

    fun get3TowerV(): GroupBlock {
        val blockMatrix = listOf(listOf(true, true, true))
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }

    fun getZ(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true, true, false),
            listOf(false, true, false),
            listOf(false, true, true),
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }

    fun getReversedZ(): GroupBlock {
        val blockMatrix = listOf(
            listOf(false, true, true),
            listOf(false, true, false),
            listOf(true, true, false),
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }

    fun get4TowerV(): GroupBlock {
        val blockMatrix = listOf(
            listOf(true),
            listOf(true),
            listOf(true),
            listOf(true),
        )
        return GroupBlock(
            0f,
            0f,
            50f,
            blockMatrix,
            context)
    }
     fun get4TowerH(): GroupBlock {
         val blockMatrix = listOf(listOf(true, true, true, true))
         return GroupBlock(
             0f,
             0f,
             50f,
             blockMatrix,
             context)
     }
}