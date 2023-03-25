package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class BasicVoxelMaterial(val material: VoxelMaterialType):VoxelMaterial() {
    override fun getMaterial(x: Int, y: Int, z: Int): VoxelMaterialType {
        return material
    }
}