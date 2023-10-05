package com.github.kevindagame.mocks

import com.github.kevindagame.voxelsniper.block.BlockFace
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.blockstate.IBlockState
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

class VoxelBlockMock : IBlock {
    override fun getLocation(): BaseLocation {
        TODO("Not yet implemented")
    }

    override fun getMaterial(): VoxelMaterial {
        TODO("Not yet implemented")
    }

    override fun setMaterial(material: VoxelMaterial?) {
        TODO("Not yet implemented")
    }

    override fun setMaterial(material: VoxelMaterial?, applyPhysics: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getFace(block: IBlock?): BlockFace? {
        TODO("Not yet implemented")
    }

    override fun getRelative(x: Int, y: Int, z: Int): IBlock {
        TODO("Not yet implemented")
    }

    override fun getRelative(face: BlockFace?): IBlock {
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
