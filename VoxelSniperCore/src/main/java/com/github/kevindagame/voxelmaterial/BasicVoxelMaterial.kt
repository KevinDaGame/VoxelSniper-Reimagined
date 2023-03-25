package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class BasicVoxelMaterial(val material: VoxelMaterialType):VoxelMaterial() {
    override fun getMaterial(location: BaseLocation): VoxelMaterialType {
        return material
    }
}