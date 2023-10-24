package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockstate

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.block.FabricBlock
import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.blockstate.AbstractBlockState
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.minecraft.block.BlockState
import java.util.logging.Level

class FabricBlockState(val block: FabricBlock, blockState: BlockState) : AbstractBlockState(block) {
    override fun getMaterial(): VoxelMaterial {
        return block.material
    }

    override fun getBlockData(): IBlockData {
        return block.blockData
    }

    override fun update(): Boolean {
        return this.update(false)
    }

    override fun update(force: Boolean): Boolean {
        return this.update(force, true)
    }

    override fun update(force: Boolean, applyPhysics: Boolean): Boolean {
        if (this.world == null) {
            return true
        }
        val block = getBlock()

        if (block.getMaterial() !== this.material && !force) {
            return false
        }
        return try {
            block.setBlockData(blockData, applyPhysics)
            true
        } catch (e: Exception) {
            VoxelSniper.voxelsniper.getLogger().log(Level.WARNING, "Failed to set BlockData", e)
            false
        }

    }

    companion object {

    }
}