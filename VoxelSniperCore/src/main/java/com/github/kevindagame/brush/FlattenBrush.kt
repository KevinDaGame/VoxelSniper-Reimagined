package com.github.kevindagame.brush

import com.github.kevindagame.brush.perform.PerformerBrush
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Shapes
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.util.brushOperation.BlockOperation
import com.github.kevindagame.voxelsniper.location.BaseLocation

class FlattenBrush : PerformerBrush() {
    override fun info(vm: VoxelMessage) {
        vm.brushName(name)
    }

    override fun doArrow(v: SnipeData) {
        flatten(v)
    }

    override fun doPowder(v: SnipeData) {
        flatten(v)
    }

    //TODO Ignore fluids
    fun flatten(v: SnipeData) {
        val y = targetBlock.y
        val locations = Shapes.ball(targetBlock.location, v.brushSize.toDouble(), false)
        for (location in locations) {
            if (location.y <= y) {
                if (!location.block.material.isAir) continue
                val locationBelow = BaseLocation(location.world, location.x, location.y - 1, location.z)
                val blockBelow = locationBelow.block
                addOperation(BlockOperation(location, location.block.blockData, blockBelow.blockData.copy))
            }
            if (location.y > y) {
                if (location.block.material.isAir) continue
                val locationAbove = BaseLocation(location.world, location.x, location.y + 1, location.z)
                val blockAbove = locationAbove.block
                addOperation(BlockOperation(location, location.block.blockData, blockAbove.blockData.copy))
            }
        }
    }
}