package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockstate

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.block.FabricBlock
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.blockstate.AbstractBlockState
import com.github.kevindagame.voxelsniper.blockstate.IBlockState
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.minecraft.block.BlockState

class FabricBlockState(val block: FabricBlock, blockState:BlockState): AbstractBlockState(block) {
    override fun getMaterial(): VoxelMaterial {
        TODO("Not yet implemented")
    }

    override fun getBlockData(): IBlockData {
        TODO("Not yet implemented")
    }

    override fun update(): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(force: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(force: Boolean, applyPhysics: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    companion object {

    }
}