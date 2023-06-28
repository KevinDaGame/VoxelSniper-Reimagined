package com.github.kevindagame.voxelsniperforge.blockdata.waterlogged

import com.github.kevindagame.voxelsniper.blockdata.waterlogged.IWaterlogged
import com.github.kevindagame.voxelsniperforge.blockdata.ForgeBlockData
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties

open class ForgeWaterlogged(state: BlockState?) : ForgeBlockData(state), IWaterlogged {
    override fun isWaterlogged(): Boolean {
        return get(BlockStateProperties.WATERLOGGED)
    }

    override fun setWaterlogged(waterlogged: Boolean) {
        set(BlockStateProperties.WATERLOGGED, waterlogged)
    }
}
