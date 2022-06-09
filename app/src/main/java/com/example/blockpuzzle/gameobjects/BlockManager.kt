package com.example.blockpuzzle.gameobjects

import android.content.Context
import android.graphics.Canvas
import kotlin.random.Random

class BlockManager(private val canvas: Context?) {
    private val factory = BlockFactory(canvas)
    private val blocks: List<GroupBlock> = buildList {
        add(factory.getOne())
        add(factory.get2TowerH())
        add(factory.get2TowerV())
        add(factory.get3TowerH())
        add(factory.get3TowerV())
    }

    fun getById(id: Int): GroupBlock {
        if (id >= blocks.size || id < 0) {
            return blocks[0]
        }
        return blocks[id]
    }

    fun getRandomBlock(): GroupBlock {
        return getById(Random.nextInt(0, blocks.size)).clone()
    }

    fun setMaxWidth(width: Float) {
        for (block in blocks) {
            block.setSizeMaximum(width)
            block.setChildSizeNormal(width * 0.4f)
        }
    }

}