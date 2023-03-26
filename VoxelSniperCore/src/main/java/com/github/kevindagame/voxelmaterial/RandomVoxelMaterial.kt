package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class RandomVoxelMaterial(val materials: List<IBlockData>): VoxelMaterial() {
    override fun getMaterial(location: BaseLocation): IBlockData {
        return materials.random()
    }
}