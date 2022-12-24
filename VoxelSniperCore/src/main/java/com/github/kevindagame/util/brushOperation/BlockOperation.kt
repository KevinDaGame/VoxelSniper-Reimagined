package com.github.kevindagame.util.brushOperation

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

/**
 * Operation that is performed on a block. This operation modifies blockData.
 */
class BlockOperation(location: BaseLocation, val oldData: IBlockData, var newData: IBlockData) :
    BrushOperation(location) {
    private var applyPhysics = true

    constructor(location: BaseLocation, oldData: IBlockData, newData: IBlockData, applyPhysics: Boolean) : this(
        location,
        oldData,
        newData
    ) {
        this.applyPhysics = applyPhysics
    }

    fun applyPhysics(): Boolean {
        return applyPhysics
    }

    /**
     * @param newMaterial the material to create blockData for
     */
    fun setNewMaterial(newMaterial: VoxelMaterial) {
        newData = newMaterial.createBlockData()
    }

    override fun perform(undo: Undo): Boolean {
        val block = location.block
        undo.put(block)
        block.setBlockData(newData, applyPhysics())
        return false
    }
}