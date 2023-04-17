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
    private var checkRange = 5
    override fun arrow(v: SnipeData) {
        stackSnow(v)
    }

    override fun powder(v: SnipeData) {
        stackSnow(v)
    }

    override fun info(vm: VoxelMessage) {}
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
            if (block.material.equals(VoxelMaterial.SNOW)) layers++
            if (layers >= 8) {
                addOperation(
                    BlockOperation(
                        block.location,
                        block.blockData,
                        VoxelMaterial.SNOW_BLOCK.createBlockData()
                    )
                )
                continue
            }
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
        var heightSum = 0.0
        var count = 0
        for (x in -checkRange..checkRange) {
            for (z in -checkRange..checkRange) {
                if (x == 0 && z == 0) continue
                val rel = block.getRelative(x, 0, z)
                if (rel.blockData is ISnow) {
                    val snow = rel.blockData as ISnow
                    heightSum += snow.getLayers() / 8.0
                    count++
                    continue
                }
                if (rel.material.isSolid) {
                    heightSum += 1
                }
                count++
            }
        }
        return heightSum / count
    }
}
