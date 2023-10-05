package com.github.kevindagame.mocks

import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

class VoxelMaterialMock(key: String): VoxelMaterial("mock", key) {
    override fun createBlockData(): IBlockData {
        TODO("Not yet implemented")
    }

    override fun isAir(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTransparent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBlock(): Boolean {
        TODO("Not yet implemented")
    }

    override fun createBlockData(s: String?): IBlockData {
        TODO("Not yet implemented")
    }
}