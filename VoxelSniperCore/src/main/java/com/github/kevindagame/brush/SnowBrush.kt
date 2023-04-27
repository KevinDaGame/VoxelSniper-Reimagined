package com.github.kevindagame.brush

import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Shapes
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.util.brushOperation.BlockOperation
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.blockdata.snow.ISnow
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

/**
 * @author KevDaDev
 */
class SnowBrush : AbstractBrush() {
    private var checkRange = 7
    override fun arrow(v: SnipeData) {
        stackSnowTest(v)
    }

    override fun powder(v: SnipeData) {
        stackSnow(v)
    }

    override fun info(vm: VoxelMessage) {}
    private fun stackSnowTest(v: SnipeData) {
        val block = lastBlock
        if (!(block.material.isAir || block.material.equals(VoxelMaterial.SNOW)) || !block.getRelative(
                0,
                -1,
                0
            ).material.isSolid
        ) return
        val averageHeight = getAverageHeight(block)
        var layers = (averageHeight * 8).toInt()
        val blockData = VoxelMaterial.SNOW.createBlockData() as ISnow
        blockData.setLayers(
            layers.coerceIn(
                blockData.getMinimumLayers(),
                blockData.getMaximumLayers()
            )
        )
        addOperation(BlockOperation(block.location, block.blockData, blockData))
    }

    private fun stackSnow(v: SnipeData) {
        val positions = Shapes.ball(
            targetBlock.location,
            v.brushSize.toDouble(), false
        )
        for (pos in positions) {
            val block = pos.block
            if (!(block.material.isAir || block.material.equals(VoxelMaterial.SNOW)) || !block.getRelative(
                    0,
                    -1,
                    0
                ).material.isSolid
            ) continue
            val averageHeight = getAverageHeight(block)
            var layers = (averageHeight * 8).toInt()
            val blockData = VoxelMaterial.SNOW.createBlockData() as ISnow
            blockData.setLayers(
                layers.coerceIn(
                    blockData.getMinimumLayers(),
                    blockData.getMaximumLayers()
                )
            )
            addOperation(BlockOperation(block.location, block.blockData, blockData))
        }
    }

    private fun getAverageHeight(block: IBlock): Double {
        //find the nearest block that slopes up (is 1 block higher than block)
        // look along the x and z axis for the nearest block that slopes up
        // If found, you can stop
        // If not found, expand the search radius by 1 block, until MAX_CHECK_RANGE is reached
        var closestHigherBlock: IBlock? = null
        var closestHigherDistance = checkRange.toDouble()
        var closestLowerBlock: IBlock? = null
        var closestLowerDistance = checkRange.toDouble()

        for (x in -checkRange..checkRange) {
            for (z in -checkRange..checkRange) {
                val relativeBlock = block.getRelative(x, 0, z)
                val dist = block.location.distance(relativeBlock.location)
                if (relativeBlock.material.isSolid && !relativeBlock.material.equals(VoxelMaterial.SNOW)) {
                    if (dist < closestHigherDistance) {
                        closestHigherBlock = relativeBlock
                        closestHigherDistance = dist
                    }
                } else if ((relativeBlock.material.isAir) && (relativeBlock.getRelative(
                        0,
                        -1,
                        0
                    ).material.isAir || relativeBlock.getRelative(0, -1, 0).material.equals(VoxelMaterial.SNOW))
                ) {
                    if (dist < closestLowerDistance) {
                        closestLowerBlock = relativeBlock
                        closestLowerDistance = dist
                    }
                }
            }
        }

        if (closestHigherBlock == null && closestLowerBlock == null) return 0.125
        val totalDistance = closestHigherDistance + closestLowerDistance
        //if there is a lower block right next to it. return .125
        //if there is no higher block, return 0.125
        return if (totalDistance == 0.0) {
            // avoid division by zero
            return 0.0
        }else if (closestHigherBlock == null) {
            0.125
        } else if (closestLowerDistance == 1.0){
            0.125
        } else {
            closestLowerDistance / totalDistance
        }

    }
}
