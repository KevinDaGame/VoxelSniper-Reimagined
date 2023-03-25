package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class RandomVoxelMaterial(val materials: List<VoxelMaterialType>): VoxelMaterial() {
    override fun getMaterial(location: BaseLocation): VoxelMaterialType {
        return materials.random()
    }
}