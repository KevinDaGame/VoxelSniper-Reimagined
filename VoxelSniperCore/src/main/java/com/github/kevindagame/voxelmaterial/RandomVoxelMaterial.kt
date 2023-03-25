package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class RandomVoxelMaterial(val materials: List<VoxelMaterialType>): VoxelMaterial() {
    override fun getMaterial(x: Int, y: Int, z: Int): VoxelMaterialType {
        return materials.random()
    }
}