package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.block

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockdata.FabricBlockData
import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockstate.FabricBlockState
import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material.FabricMaterial
import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.world.FabricWorld
import com.github.kevindagame.voxelsniper.block.AbstractBlock
import com.github.kevindagame.voxelsniper.block.BlockFace
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.blockstate.IBlockState
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FabricBlock(location: BaseLocation) :
    AbstractBlock(
        location, FabricMaterial.fromFabricBlock(
            ((location.world) as FabricWorld).world.getBlockState(
                BlockPos(location.blockX, location.blockY, location.blockZ)
            ).block
        )
    ) {

    val blockPos = BlockPos(location.blockX, location.blockY, location.blockZ)
    override fun setMaterial(material: VoxelMaterial?) {
        setMaterial(material, true)
    }

    override fun setMaterial(material: VoxelMaterial?, applyPhysics: Boolean) {
        ((location.world) as FabricWorld).world.setBlockState(
            BlockPos(location.blockX, location.blockY, location.blockZ),
            ((material as FabricMaterial).block.defaultState)
        )
    }

    override fun getFace(block: IBlock?): BlockFace? {
        TODO("Not yet implemented")
    }

    override fun getBlockData(): IBlockData {
        return FabricBlockData(
            ((location.world) as FabricWorld).world.getBlockState(
                BlockPos(location.blockX, location.blockY, location.blockZ)
            )
        )
    }

    override fun setBlockData(blockData: IBlockData?) {
        setBlockData(blockData, true)
    }

    override fun setBlockData(blockData: IBlockData?, applyPhysics: Boolean) {
        val world = getFabricWorld()
        val oldState = world.getBlockState(this.blockPos)
        val newState = (blockData as FabricBlockData).state

        if (oldState.hasBlockEntity() && newState.block !== oldState.block) {
            world.removeBlockEntity(this.blockPos)
        }

        if (applyPhysics) {
            world.setBlockState(this.blockPos, newState, 3)
        } else {
            throw UnsupportedOperationException("Fabric does not support no physics yet")
            //copied code from forge. Will need refactor
//            try {
//                if (newState is ExtendedBlockState) {
//                    newState.setShouldUpdate(false)
//                }
//                val success: Boolean = world.setBlock(this.pos, newState, 2 or 16) // NOTIFY | NO_OBSERVER
//                if (success) {
//                    getFabricWorld().sendBlockUpdated(
//                        this.pos,
//                        old,
//                        newState,
//                        3
//                    )
//                }
//            } finally {
//                if (newState is ExtendedBlockState) newState.setShouldUpdate(true)
//            }
        }
//        world.setBlock(this.pos, newState, 3)

        material = blockData.material
    }

    override fun isEmpty(): Boolean {
        return getMaterial().isAir
    }

    override fun isLiquid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getState(): IBlockState {
        val blockState: BlockState = ((location.world) as FabricWorld).world.getBlockState(
            BlockPos(location.blockX, location.blockY, location.blockZ)
        )
        return FabricBlockState(this, blockState)
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

    private fun getFabricWorld(): World {
        return ((location.world) as FabricWorld).world
    }
}