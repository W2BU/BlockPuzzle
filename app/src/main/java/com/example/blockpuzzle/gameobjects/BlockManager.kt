package com.example.blockpuzzle.gameobjects

import android.content.Context
import kotlin.random.Random

class BlockManager(context: Context?) {
    private val factory = BlockFactory(context)
    private val blocks: List<GroupBlock> = buildList {
        add(factory.getCubeOne())
        add(factory.getCubeTwo())
        add(factory.getCubeThree())
        add(factory.get2TowerH())
        add(factory.get2TowerV())
        add(factory.get3TowerH())
        add(factory.get3TowerV())
        add(factory.getZ())
        add(factory.getReversedZ())
        add(factory.get4TowerV())
        add(factory.get4TowerH())
    }

    private fun getById(id: Int): GroupBlock {
        if (id >= blocks.size || id < 0) {
            return blocks[0]
        }
        return blocks[id]
    }

    fun getRandomBlock(): GroupBlock {
        return getById(Random.nextInt(0, blocks.size)).clone()
    }

    fun setBlockWidth(width: Float) {
        for (block in blocks) {
            block.setChildSizeNormal(width)
        }
    }

}