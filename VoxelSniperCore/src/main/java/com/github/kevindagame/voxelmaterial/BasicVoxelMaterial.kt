package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class BasicVoxelMaterial(val mat: IBlockData):VoxelMaterial() {
    constructor(material: VoxelMaterialType):this(material.createBlockData())
    override fun getMaterial(location: BaseLocation): IBlockData {
        return mat
    }
}