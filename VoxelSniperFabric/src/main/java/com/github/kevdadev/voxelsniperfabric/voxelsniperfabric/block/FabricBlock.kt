package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.block

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material.FabricMaterial
import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.world.FabricWorld
import com.github.kevindagame.voxelsniper.block.AbstractBlock
import com.github.kevindagame.voxelsniper.block.BlockFace
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.blockstate.IBlockState
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.minecraft.util.math.BlockPos

class FabricBlock(location: BaseLocation) :
    AbstractBlock(
        location, FabricMaterial.fromFabricBlock(
            ((location.world) as FabricWorld).world.getBlockState(
                BlockPos(location.blockX, location.blockY, location.blockZ)
            ).block
        )
    ) {
    override fun setMaterial(material: VoxelMaterial?) {
        TODO("Not yet implemented")
    }

    override fun setMaterial(material: VoxelMaterial?, applyPhysics: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getFace(block: IBlock?): BlockFace? {
        TODO("Not yet implemented")
    }

    override fun getBlockData(): IBlockData {
        TODO("Not yet implemented")
    }

    override fun setBlockData(blockData: IBlockData?) {
        TODO("Not yet implemented")
    }

    override fun setBlockData(blockData: IBlockData?, applyPhysics: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isLiquid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getState(): IBlockState {
        TODO("Not yet implemented")
    }

    override fun isBlockFacePowered(face: BlockFace?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBlockFaceIndirectlyPowered(face: BlockFace?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBlockIndirectlyPowered(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBlockPowered(): Boolean {
        TODO("Not yet implemented")
    }
}